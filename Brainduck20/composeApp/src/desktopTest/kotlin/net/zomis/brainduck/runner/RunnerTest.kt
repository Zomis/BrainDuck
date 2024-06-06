package net.zomis.brainduck.runner

import com.google.common.truth.Truth
import net.zomis.brainduck.Brainfuck
import net.zomis.brainduck.BrainfuckInput
import net.zomis.brainduck.BrainfuckOutput
import net.zomis.brainduck.InfiniteMemory
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class RunnerTest {

    @TestFactory
    fun codeTests(): DynamicContainer {
        return TestBuilder().apply {
            listOf("Fibonacci", "FizzBuzz", "HelloWorld").forEach {
                file(it) {
                    successfullyRuns()
                    outputs("$it.bf.output".fileResource())
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

    private fun asciiTableText(): String {
        return (0..255).joinToString("\n") {
            "%03d".format(it) + ": " + it.toChar()
        } + "\n"
    }

}
