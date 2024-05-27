package net.zomis.brainduck

import net.zomis.brainduck.ast.Syntax
import net.zomis.brainduck.runner.Runner

class BrainfuckProgram(
    val code: List<Syntax>,
    val memory: BrainfuckMemory,
) {
    val codePosition: List<Int> = emptyList()

    fun run(runner: Runner) {
        TODO()
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
