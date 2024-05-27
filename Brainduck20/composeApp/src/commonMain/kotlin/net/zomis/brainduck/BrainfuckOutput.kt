package net.zomis.brainduck

fun interface BrainfuckOutput {
    object SystemOut : BrainfuckOutput {
        override fun write(value: Int) {
            print(value.toChar())
        }
    }

    fun write(value: Int)
}
