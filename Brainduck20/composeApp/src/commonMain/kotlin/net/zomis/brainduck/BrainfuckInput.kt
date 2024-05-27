package net.zomis.brainduck

fun interface BrainfuckInput {
    object NoInput : BrainfuckInput {
        override fun read(): Int = error("NoInput has no input")
    }

    fun read(): Int
}
