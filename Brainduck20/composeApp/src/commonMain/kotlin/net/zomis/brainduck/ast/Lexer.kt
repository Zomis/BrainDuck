package net.zomis.brainduck.ast

import net.zomis.brainduck.TokenizedProgram

interface LexerStrategy {
    fun parse(ch: Char): LexerStrategy
    fun end()
}

class Lexer {
    private val result = mutableListOf<Token>()
    private var position = 1
    private var currentLine = 1
    private var column = 1
    private var file = "."

    fun tokenize(text: String): TokenizedProgram {
        var strategy: LexerStrategy = DefaultStrategy()

        for ((lineIndex, lineText) in text.lines().withIndex()) {
            currentLine = lineIndex + 1
            column = 1
            for (c in lineText) {
                strategy = strategy.parse(c)
            }
        }
        strategy.end()
        return TokenizedProgram(result.toList())
    }

    private inner class CommentStrategy : LexerStrategy {
        private val text = StringBuilder()

        override fun parse(ch: Char): LexerStrategy {
            val token = TokenData.instructionOrNull(ch)
            return when (token) {
                is TokenData.Instruction -> {
                    result.add(nextTokenInfo(TokenData.Comment(text.toString())))
                    DefaultStrategy().parse(ch)
                }
                else -> {
                    text.append(text.toString())
                    this
                }
            }
        }

        override fun end() {}
    }

    private inner class DefaultStrategy : LexerStrategy {
        override fun parse(ch: Char): LexerStrategy {
            val bfCommand = TokenData.instructionOrNull(ch)
            return if (bfCommand != null) {
                result.add(nextTokenInfo(bfCommand))
                this
            } else {
                CommentStrategy().parse(ch)
            }
        }

        override fun end() {}
    }

    private fun nextTokenInfo(data: TokenData): Token {
        val tokenLength = 1
        val info = TokenInfo(position, tokenLength, currentLine, column, file)
        position += tokenLength
        column++
        return Token(info, data)
    }


}