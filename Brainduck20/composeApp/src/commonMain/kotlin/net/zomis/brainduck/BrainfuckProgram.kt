package net.zomis.brainduck

import net.zomis.brainduck.analyze.AnalyzeResult
import net.zomis.brainduck.analyze.BrainfuckAnalyzer
import net.zomis.brainduck.analyze.BrainfuckRuntime
import net.zomis.brainduck.ast.Syntax
import net.zomis.brainduck.ast.SyntaxData
import net.zomis.brainduck.runner.BrainfuckListener
import net.zomis.brainduck.runner.Runner
import net.zomis.brainduck.runner.UntilEnd

class BrainfuckProgram(
    override val code: BrainfuckCode,
    override val memory: BrainfuckMemory,
) : BrainfuckRuntime {
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
        val syntax = syntaxStack.last().current()
        listeners.forEach {
            it.before(syntax, this)
        }
        val data = syntax.data
        when (data) {
            is SyntaxData.Advanced -> TODO()
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
        listeners.forEach {
            it.after(syntax, this)
        }
    }

    private fun nextSyntax() {
        syntaxStack.last().next()
    }

    fun analyze(input: BrainfuckInput, output: BrainfuckOutput, analyzers: List<BrainfuckAnalyzer<*, *>>): List<AnalyzeResult<*, *>> {
        analyzers.forEach { it.beforeStart(this) }
        val adapters = analyzers.map { AnalyzeAdapter(it) }
        run(UntilEnd, input, output, adapters)
        analyzers.forEach { it.after(this) }
        return adapters.map { it.createResult() }
    }

    private class AnalyzeAdapter<R, C>(val analyzer: BrainfuckAnalyzer<R, C>) : BrainfuckListener {
        private val cells = mutableMapOf<Int, C?>()

        override fun before(syntax: Syntax, runtime: BrainfuckRuntime) {
            val index = runtime.memory.currentIndex
            val cell = cells[index]
            val result = analyzer.beforePerform(cell, runtime, syntax)
            if (result != cell) cells[index] = result
        }

        override fun after(syntax: Syntax, runtime: BrainfuckRuntime) {
            val index = runtime.memory.currentIndex
            val cell = cells[index]
            val result = analyzer.afterPerform(cell, runtime, syntax)
            if (result != cell) cells[index] = result
        }

        fun createResult(): AnalyzeResult<R?, C> {
            return AnalyzeResult(analyzer.result(), cells)
        }

    }

}
