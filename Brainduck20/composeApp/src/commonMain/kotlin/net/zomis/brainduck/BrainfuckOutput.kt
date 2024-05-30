package net.zomis.brainduck

fun interface BrainfuckOutput {
    object SystemOut : BrainfuckOutput {
        override fun write(value: Int) {
            print(value.toChar())
        }
    }
    class StringWriter : BrainfuckOutput {
        private val string = StringBuilder()

        override fun write(value: Int) {
            string.append(value.toChar())
        }

        override fun toString(): String = string.toString()
    }

    fun write(value: Int)
}
