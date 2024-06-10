package net.zomis.brainduck.analyze

class CellTag(val tag: String, val data: Int)

interface CellTagger {
    fun tags(): List<CellTag>
}

interface GlobalCellTagger {
    fun tags(): Map<Int, List<CellTag>>
}
