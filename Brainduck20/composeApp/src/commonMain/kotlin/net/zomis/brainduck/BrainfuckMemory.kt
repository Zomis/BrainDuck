package net.zomis.brainduck

interface BrainfuckMemory {
    val currentIndex: Int
    val indices: IntRange
    fun move(offset: Int)
    fun currentValue(): Int
    fun changeValue(offset: Int)
}
