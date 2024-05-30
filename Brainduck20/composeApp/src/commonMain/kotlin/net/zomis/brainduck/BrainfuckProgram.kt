package net.zomis.brainduck

import net.zomis.brainduck.ast.Syntax
import net.zomis.brainduck.ast.SyntaxData
import net.zomis.brainduck.runner.BrainfuckListener
import net.zomis.brainduck.runner.Runner

class BrainfuckProgram(
    val code: BrainfuckCode,
    val memory: BrainfuckMemory,
) {
    private class SyntaxPosition(val syntax: List<Syntax>) {
        private var position = 0
        fun isFinished() = syntax.size == position
        fun current() = syntax[position]
        fun next() {
            position++
        }

        fun restart() {
            position = 0
        }

        override fun toString(): String = "$position/${syntax.lastIndex}"
    }

    private val syntaxStack = mutableListOf<SyntaxPosition>(SyntaxPosition(code.syntax.children))

    fun run(runner: Runner, input: BrainfuckInput, output: BrainfuckOutput, listeners: List<BrainfuckListener> = emptyList()) {
        runner.run(this, input, output, listeners)
    }

    fun isFinished(): Boolean = syntaxStack.singleOrNull()?.isFinished() == true

    fun runSyntax(input: BrainfuckInput, output: BrainfuckOutput, listeners: List<BrainfuckListener>) {
        val data = syntaxStack.last().current().data
        when (data) {
            SyntaxData.Advanced -> TODO()
            is SyntaxData.ChangeValue -> memory.changeValue(data.delta)
            SyntaxData.Comment -> {}
            is SyntaxData.Move -> memory.move(data.delta)
            SyntaxData.Read -> memory.set(input.read())
            is SyntaxData.Root -> TODO()
            is SyntaxData.WhileNotZero -> {
                if (memory.currentValue() != 0) {
                    syntaxStack.add(SyntaxPosition(data.children))
                } else {
                    nextSyntax()
                }
            }
            SyntaxData.Write -> output.write(memory.currentValue())
            SyntaxData.EndWhile -> {
                if (memory.currentValue() == 0) {
                    syntaxStack.removeLast()
                    nextSyntax()
                } else {
                    syntaxStack.last().restart()
                }
            }
        }
        if (data !is SyntaxData.EndWhile && data !is SyntaxData.WhileNotZero) nextSyntax()
    }

    private fun nextSyntax() {
        syntaxStack.last().next()
    }

}
