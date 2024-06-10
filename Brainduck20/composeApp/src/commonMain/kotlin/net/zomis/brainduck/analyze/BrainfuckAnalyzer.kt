package net.zomis.brainduck.analyze

import net.zomis.brainduck.BrainfuckCode
import net.zomis.brainduck.BrainfuckMemory
import net.zomis.brainduck.ast.Syntax

fun <T : Any> T?.orElse(factory: () -> T) = this ?: factory()

interface BrainfuckRuntime {
    val code: BrainfuckCode
    val memory: BrainfuckMemory
}

interface BrainfuckAnalyzer<Result, CellResult> {

    fun result(): Result? = null

    fun beforeStart(runtime: BrainfuckRuntime) {}
    fun after(runtime: BrainfuckRuntime) {}

    fun print() {}

    fun beforePerform(cell: CellResult?, runtime: BrainfuckRuntime, command: Syntax): CellResult? = cell
    fun afterPerform(cell: CellResult?, runtime: BrainfuckRuntime, command: Syntax): CellResult? = cell

}

data class AnalyzeResult<Result, CellResult>(val result: Result?, val cells: Map<Int, CellResult?>)
