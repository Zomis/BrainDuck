package net.zomis.brainduck

interface BrainfuckMemory {
    val currentIndex: Int
    val indices: IntRange
    fun move(offset: Int)
    fun currentValue(): Int
    fun changeValue(offset: Int)
    fun set(value: Int)

    companion object {
        fun default(): BrainfuckMemory = LimitedMemory(0..30_000)
    }
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

class InfiniteMemory(private val wrapping: Boolean) : BrainfuckMemory {
    private val negativeCells = mutableListOf<Int>()
    private val positiveCells = mutableListOf<Int>(0)
    override var currentIndex: Int = 0
    override val indices: IntRange get() = negativeCells.size..positiveCells.lastIndex

    override fun move(offset: Int) {
        currentIndex += offset
        ensureRange()
    }

    private fun ensureRange() {
        while (currentIndex < -negativeCells.size) {
            negativeCells.add(0)
        }
        while (currentIndex >= positiveCells.size) {
            positiveCells.add(0)
        }
    }

    private infix fun Int.fmod(other: Int) = ((this % other) + other) % other

    override fun currentValue(): Int {
        return if (currentIndex >= 0) positiveCells[currentIndex] else negativeCells[-currentIndex - 1]
    }

    override fun changeValue(offset: Int) {
        set(currentValue() + offset)
    }

    override fun set(value: Int) {
        val fmodValue = if (wrapping) value fmod 256 else value
        if (currentIndex >= 0) {
            positiveCells[currentIndex] = fmodValue
        } else {
            negativeCells[-currentIndex - 1] = fmodValue
        }
    }

}