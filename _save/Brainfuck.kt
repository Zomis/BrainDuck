package net.zomis.brainduck

object Brainfuck {

    fun code(code: String): Code = Code(code)

}

interface Command {
    fun execute(runner: Runner)
}
enum class Commands(val char: Char, val command: Runner.() -> Unit) {
    NONE(Char(0), {  }),
    NEXT('>', { memory.memoryIndex++ }),
    PREVIOUS('<', { memory.memoryIndex-- }),
    WRITE('.', { output.write(memory.value) }),
    READ(',', { memory.value = input.read() }),
    ADD('+', { memory.value++ }),
    SUBTRACT('-', { memory.value-- }),
    WHILE('[', {
        if (memory.value == 0) {
            code.search(END_WHILE, 1)
        }
    }),
    END_WHILE(']', {
        if (memory.value != 0) {
            code.search(WHILE, -1)
        }
    })

}
class Runner(val code: Code, val memory: Memory, val input: Input, val output: Output) {
    fun run() {
        code.reset()
        val lookup = Commands.values().associateBy { it.char }
        var runs = 0
        while (!code.finished && runs < 10000) {
            val ch = code.char
            lookup[ch]?.command?.invoke(this)
            code.codeIndex++
            runs++
        }
    }
}
class Code(val code: String) {
    var codeIndex = 0
    val char get() = code[codeIndex]
    val finished get() = codeIndex >= code.length

    fun reset() {
        codeIndex = 0
    }

    fun search(target: Commands, direction: Int) {
        var nested = 1
        do {
            codeIndex += direction
            when (target.char) {
                '[' -> {
                    if (char == '[') {
                        nested--
                    } else if (char == ']') {
                        nested++
                    }
                }
                ']' -> {
                    if (char == '[') {
                        nested++
                    } else if (char == ']') {
                        nested--
                    }
                }
            }
        } while (nested > 0)

    }

    fun run(input: Input = NoInput): String {
        val output = StringBuilderOutput()
        val runner = Runner(this, Memory(1000), input, output)
        runner.run()
        return output.text
    }
}
class Memory(size: Int) {
    val cells = (1..size).map { 0 }.toIntArray()
    var memoryIndex = 0
    var value: Int
        get() = cells[memoryIndex]
        set(value) { cells[memoryIndex] = value }
}
interface Input {
    fun read(): Int
}
object NoInput : Input {
    override fun read(): Int = throw UnsupportedOperationException("NoInput has no input")
}

interface Output {
    fun write(value: Int)
}
class StringBuilderOutput : Output {
    private val builder = StringBuilder()
    val text get() = builder.toString()
    override fun write(value: Int) {
        builder.append(Char(value))
    }
}

interface RunStrategy {
    fun start(runner: Runner): Boolean
    fun next(runner: Runner): Boolean
}
