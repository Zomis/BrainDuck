package net.zomis.brainduck.compose

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

interface MemoryCell {
    val address: Int
    val value: State<Int>
    val labels: State<Map<String, Int>>
}

class MemoryCellImpl(override val address: Int) : MemoryCell {
    override val value: State<Int> = mutableStateOf(0)
    override val labels: State<Map<String, Int>> = mutableStateOf(emptyMap())
}
