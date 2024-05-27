package net.zomis.brainduck

import net.zomis.brainduck.ast.Token

class TokenizedProgram(val tokens: List<Token>) {

    fun parse(): BrainfuckProgram {
        println("tokens ${tokens.size}")
        tokens.forEach {
            println(it)
        }
        TODO()
    }

}
