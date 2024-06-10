package net.zomis.brainduck.analyzers

import net.zomis.brainduck.analyze.BrainfuckAnalyzer
import net.zomis.brainduck.analyze.BrainfuckRuntime
import net.zomis.brainduck.analyze.orElse
import net.zomis.brainduck.ast.Syntax
import net.zomis.brainduck.ast.SyntaxData

data class MemoryCellIO(val reads: Int, val writes: Int) {
    fun addRead() = MemoryCellIO(reads + 1, writes)
    fun addWrite() = MemoryCellIO(reads, writes + 1)
}

class MemoryIO : BrainfuckAnalyzer<Nothing, MemoryCellIO> {

    private fun createMemoryData(): MemoryCellIO = MemoryCellIO(0, 0)

    override fun beforePerform(cell: MemoryCellIO?, runtime: BrainfuckRuntime, command: Syntax): MemoryCellIO? {
        if (command.data is SyntaxData.Read) {
            return cell.orElse(::createMemoryData).addRead()
        }
        if (command.data is SyntaxData.Write) {
            return cell.orElse(::createMemoryData).addWrite()
        }
        return cell
    }

}
