package net.zomis.brainduck

interface BrainfuckMemory {
    val currentIndex: Int
    val indices: IntRange
    fun move(offset: Int)
    fun currentValue(): Int
    fun changeValue(offset: Int)
    fun set(value: Int)
}

class LimitedMemory(range: IntRange) : BrainfuckMemory {
    override var currentIndex: Int = 0
    override val indices: IntRange = range
    private val values = IntArray(range.count())

    override fun move(offset: Int) {
        require(offset + currentIndex in indices) { "OutOfBounds: $currentIndex + $offset is not within $indices" }
        currentIndex += offset
    }

    override fun currentValue(): Int = values[currentIndex]

    override fun changeValue(offset: Int) {
        values[currentIndex] += offset
    }

    override fun set(value: Int) {
        values[currentIndex] = value
    }

}