package net.zomis.brainduck

import net.zomis.brainduck.ast.SyntaxData

class BrainfuckCode(val syntax: SyntaxData.Root) {
    fun createProgram(): BrainfuckProgram {
        return createProgram(LimitedMemory(0..30_000))
    }

    fun createProgram(memory: BrainfuckMemory): BrainfuckProgram {
        return BrainfuckProgram(this, memory)
    }
}
