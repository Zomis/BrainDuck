package net.zomis.brainduck.ast

data class Token(val info: TokenInfo, val data: TokenData)

sealed interface TokenData {
    interface Instruction : TokenData

    data class Advanced(val code: String) : TokenData
    data class Comment(val text: String) : TokenData
    data object Write : Instruction
    data object Read : Instruction
    data class Plus(val count: Int) : Instruction
    data class Minus(val count: Int) : Instruction
    data class MoveLeft(val count: Int) : Instruction
    data class MoveRight(val count: Int) : Instruction
    data object StartWhile : Instruction
    data object EndWhile : Instruction

    companion object {
        fun instructionOrNull(ch: Char): Instruction? = when (ch) {
            '+' -> Plus(1)
            '-' -> Minus(1)
            '<' -> MoveLeft(1)
            '>' -> MoveRight(1)
            '[' -> StartWhile
            ']' -> EndWhile
            '.' -> Write
            ',' -> Read
            else -> null
        }
    }

}

data class TokenInfo(
    val position: Int,
    val length: Int,
    val line: Int,
    val column: Int,
    val file: String,
)
