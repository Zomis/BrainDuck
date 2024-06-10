package net.zomis.brainduck.analyzers

import net.zomis.brainduck.analyze.BrainfuckAnalyzer
import net.zomis.brainduck.analyze.BrainfuckCommand
import net.zomis.brainduck.analyze.BrainfuckRuntime
import net.zomis.brainduck.ast.Syntax
import net.zomis.brainduck.ast.SyntaxData
import kotlin.math.absoluteValue

typealias CommandsCount = Map<BrainfuckCommand, Int>

class CommandCount : BrainfuckAnalyzer<CommandsCount, Nothing> {

    private val codeInstructions = mutableMapOf<BrainfuckCommand, Int>().withDefault { 0 }

    override fun result(): CommandsCount = codeInstructions.toMap()

    override fun beforeStart(runtime: BrainfuckRuntime) {
        countRecursively(runtime.code.syntax.children)
    }

    private fun countRecursively(children: List<Syntax>) {
        for (syntax in children) {
            val key = BrainfuckCommand.from(syntax) ?: continue
            val count = if (syntax.data is SyntaxData.SteppableSyntax) syntax.data.delta.absoluteValue else 1
            codeInstructions[key] = codeInstructions.getValue(key) + count
            if (syntax.data is SyntaxData.WhileNotZero) {
                countRecursively(syntax.data.children)
            }
        }
    }

    override fun print() {
        println("Code: $codeInstructions")
    }

}
