package net.zomis.brainduck.ast

data class Token(val info: TokenInfo, val data: TokenData)

sealed interface TokenData {
    sealed interface Instruction : TokenData
    sealed class Repeatable(val count: Int)

    data class Advanced(val code: String) : TokenData
    data class Comment(val text: String) : TokenData
    data object Write : Instruction
    data object Read : Instruction
    data object Plus : Instruction, Repeatable(1)
    data object Minus : Instruction, Repeatable(-1)
    data object MoveLeft : Instruction, Repeatable(-1)
    data object MoveRight : Instruction, Repeatable(1)
    data object StartWhile : Instruction
    data object EndWhile : Instruction

    companion object {
        fun instructionOrNull(ch: Char): Instruction? = when (ch) {
            '+' -> Plus
            '-' -> Minus
            '<' -> MoveLeft
            '>' -> MoveRight
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
