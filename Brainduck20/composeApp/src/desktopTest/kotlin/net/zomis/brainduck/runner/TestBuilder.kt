package net.zomis.brainduck.runner

import com.google.common.truth.Truth
import net.zomis.brainduck.*
import net.zomis.brainduck.analyze.AnalyzeResult
import net.zomis.brainduck.analyze.BrainfuckAnalyzer
import net.zomis.brainduck.analyzers.CommandCount
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import java.io.InputStream

fun String.readFileResource(): String {
    val stream = this.fileResource() ?: throw IllegalArgumentException("$this file does not exist")
    return stream.bufferedReader().readText()
}
fun String.fileResource(): InputStream? = TestBuilder::class.java.classLoader.getResourceAsStream(this)

class TestBuilder {

    private val subnodes = mutableListOf<DynamicNode>()

    class FileContext(private val filename: String, private val input: () -> BrainfuckInput) {
        private val code = Brainfuck.tokenize(filename.readFileResource()).parse()
        val tests = mutableListOf<DynamicNode>()

        fun outputs(text: String, brainfuckMemory: BrainfuckMemory = BrainfuckMemory.default()) {
            tests.add(DynamicTest.dynamicTest("produces correct output") {
                val writer = BrainfuckOutput.StringWriter()
                code.createProgram(brainfuckMemory).run(UntilEnd, input.invoke(), writer)
                Truth.assertThat(writer.toString()).isEqualTo(text)
            })
        }

        fun outputs(fileResource: InputStream?, brainfuckMemory: BrainfuckMemory = BrainfuckMemory.default()) {
            tests.add(DynamicTest.dynamicTest("produces correct output") {
                checkNotNull(fileResource) { "Missing output for $filename" }

                val writer = BrainfuckOutput.StringWriter()
                code.createProgram(brainfuckMemory).run(UntilEnd, input.invoke(), writer)
                Truth.assertThat(writer.toString()).isEqualTo(fileResource.bufferedReader().readText())
            })
        }

        fun successfullyRuns(memory: BrainfuckMemory = LimitedMemory(0..30_000)) {
            tests.add(DynamicTest.dynamicTest("runs successfully") {
                code.createProgram(memory).run(UntilEnd, input.invoke(), BrainfuckOutput.SystemOut)
            })
        }

        fun fileInput(filename: String, block: FileContext.() -> Unit) {
            val nested = FileContext(this.filename) {
                BrainfuckInput.StringInput(filename.readFileResource())
            }.apply(block).tests
            tests.add(DynamicContainer.dynamicContainer("using input from file $filename", nested))
        }

        fun <R, C> analysis(analyzer: BrainfuckAnalyzer<R, C>, function: AnalyzeResult<R, C>.() -> Unit) {
            tests.add(DynamicTest.dynamicTest("analyze using $analyzer") {
                val result = code.createProgram().analyze(input.invoke(), BrainfuckOutput.NoOutput, listOf(analyzer))
                function.invoke(result.first() as AnalyzeResult<R, C>)
            })
        }
    }

    fun file(name: String, block: FileContext.() -> Unit) {
        val tests = FileContext("$name.bf") { BrainfuckInput.NoInput }.apply(block).tests
        subnodes.add(DynamicContainer.dynamicContainer(name, tests))
    }

    fun dynamicNode(): DynamicContainer = DynamicContainer.dynamicContainer("files", subnodes)


}