package net.zomis.brainduck.runner

import com.google.common.truth.Truth.assertThat
import net.zomis.brainduck.InfiniteMemory
import net.zomis.brainduck.analyze.BrainfuckCommand
import net.zomis.brainduck.analyzers.CommandCount
import net.zomis.brainduck.analyzers.MemoryCellIO
import net.zomis.brainduck.analyzers.MemoryIO
import net.zomis.brainduck.analyzers.RuntimeCount
import net.zomis.brainduck.ast.SyntaxData
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.TestFactory

class RunnerTest {

    @TestFactory
    fun codeTests(): DynamicContainer {
        return TestBuilder().apply {
            simpleOutput("Fibonacci")
            simpleOutput("FizzBuzz")
            simpleOutput("HelloWorld") {
                analysis(CommandCount()) {
                    result!!.assertContains(
                        BrainfuckCommand.Plus to 193,
                        BrainfuckCommand.Minus to 160,
                    )
                }
                analysis(RuntimeCount()) {
                    result!!.assertContains(
                        BrainfuckCommand.Write to 13,
                        BrainfuckCommand.Plus to 193,
                        BrainfuckCommand.Minus to 160,
                    )
                }
                analysis(MemoryIO()) {
                    assertThat(cells.entries.map { it.key to it.value }).containsExactly(
                        0 to MemoryCellIO(0, 13)
                    )
                }
            }
            file("AsciiTable") {
                successfullyRuns()
                outputs(asciiTableText())
            }
            file("JsonFormatter") {
                fileInput("JsonFormatter.bf.input") {
                    successfullyRuns(InfiniteMemory(wrapping = true))
                    outputs("JsonFormatter.bf.output".fileResource(), InfiniteMemory(wrapping = true))
                }
            }
        }.dynamicNode()
    }

    private fun TestBuilder.simpleOutput(filename: String, extra: TestBuilder.FileContext.() -> Unit = {}) {
        file(filename) {
            successfullyRuns()
            outputs("$filename.bf.output".fileResource())
            extra.invoke(this)
        }
    }

    private fun asciiTableText(): String {
        return (0..255).joinToString("\n") {
            "%03d".format(it) + ": " + it.toChar()
        } + "\n"
    }

}

private fun <K, V> Map<K, V>.assertContains(vararg values: Pair<K, V>) {
    assertThat(this.entries.map { it.key to it.value }).containsAtLeastElementsIn(values)
}
