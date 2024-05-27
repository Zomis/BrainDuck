package net.zomis.brainduck.runner

import net.zomis.brainduck.Brainfuck
import net.zomis.brainduck.BrainfuckInput
import net.zomis.brainduck.BrainfuckOutput
import org.junit.Test

class RunnerTest {

    @Test
    fun test() {
        val text = this::class.java.classLoader.getResource("test.bf")!!.readText()

        Brainfuck.tokenize(text).parse().createProgram()
            .run(UntilEnd, BrainfuckInput.NoInput, BrainfuckOutput.SystemOut)
    }

}
