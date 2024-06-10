package net.zomis.brainduck.analyzers

import net.zomis.brainduck.analyze.BrainfuckAnalyzer
import net.zomis.brainduck.analyze.BrainfuckCommand
import net.zomis.brainduck.analyze.BrainfuckRuntime
import net.zomis.brainduck.ast.Syntax
import net.zomis.brainduck.ast.SyntaxData
import kotlin.math.absoluteValue

class RuntimeCount : BrainfuckAnalyzer<CommandsCount, Nothing?> {

    private val codeCount = mutableMapOf<BrainfuckCommand, Int>().withDefault { 0 }

    override fun result(): CommandsCount = codeCount.toMap()

    override fun beforePerform(cell: Nothing?, runtime: BrainfuckRuntime, command: Syntax): Nothing? {
        val key = BrainfuckCommand.from(command) ?: return null
        val count = (command.data as? SyntaxData.SteppableSyntax)?.delta?.absoluteValue ?: 1
        codeCount[key] = codeCount.getValue(key) + count
        return null
    }

    override fun print() {
        println("RuntimeCount: $codeCount")
    }

}
