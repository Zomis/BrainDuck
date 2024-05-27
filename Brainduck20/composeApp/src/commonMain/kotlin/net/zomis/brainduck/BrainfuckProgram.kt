package net.zomis.brainduck

import net.zomis.brainduck.ast.Syntax
import net.zomis.brainduck.runner.BrainfuckListener
import net.zomis.brainduck.runner.Runner

class BrainfuckProgram(
    val code: BrainfuckCode,
    val memory: BrainfuckMemory,
) {
    private class SyntaxPosition(val syntax: List<Syntax>) {
        var position = 0
    }

    private val syntaxStack = mutableListOf<SyntaxPosition>()

    fun run(runner: Runner, input: BrainfuckInput, output: BrainfuckOutput, listeners: List<BrainfuckListener> = emptyList()) {
        runner.run(this, input, output, listeners)
    }

    fun isFinished(): Boolean {
        TODO("Not yet implemented")
    }

    fun runSyntax() {
        TODO("Not yet implemented")
    }

    private fun nextSyntax() {
        TODO()
    }

}
