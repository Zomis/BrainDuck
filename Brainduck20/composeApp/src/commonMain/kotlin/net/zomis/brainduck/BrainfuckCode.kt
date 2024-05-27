package net.zomis.brainduck

import net.zomis.brainduck.ast.SyntaxData

class BrainfuckCode(val syntax: SyntaxData.Root) {
    fun createProgram(): BrainfuckProgram {
        return BrainfuckProgram(this, LimitedMemory(0..30_000))
    }
}
