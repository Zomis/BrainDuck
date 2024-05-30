package net.zomis.brainduck

import net.zomis.brainduck.ast.*

class TokenizedProgram(val tokens: List<Token>) {
    private class LoopSyntax(val startToken: Token?) {
        val syntaxes: MutableList<Syntax> = mutableListOf()
    }
    private fun SyntaxInfo.withData(data: SyntaxData): Syntax = Syntax(this, data)

    fun parse(): BrainfuckCode {
        val awaitingTokens = mutableListOf<Token>()
        var lastToken: Token? = null
        var diff = 0
        val depthStack = mutableListOf<LoopSyntax>()
        depthStack.add(LoopSyntax(startToken = null))
        var syntaxes = depthStack.single().syntaxes

        for (token in tokens) {
            val last = lastToken
            // If the token differs, we know we can complete the current repeatable token - if any
            if (last?.data != token.data && awaitingTokens.isNotEmpty()) {
                syntaxes.add(createRepeatSyntax(awaitingTokens.toList(), diff))
                awaitingTokens.clear()
                diff = 0
            }

            val syntaxInfo = SyntaxInfo(listOf(token))
            when (token.data) {
                is TokenData.Advanced -> TODO()
                is TokenData.Comment -> syntaxes.add(syntaxInfo.withData(SyntaxData.Comment))
                TokenData.StartWhile -> {
                    depthStack.add(LoopSyntax(startToken = token))
                    syntaxes = depthStack.last().syntaxes
                }
                TokenData.EndWhile -> {
                    val popped = depthStack.removeLast()
                    syntaxes = depthStack.last().syntaxes
                    val tokens = listOf(popped.startToken) + popped.syntaxes.flatMap { it.info.tokens } + token
                    val endWhile = Syntax(syntaxInfo, SyntaxData.EndWhile)
                    syntaxes.add(Syntax(SyntaxInfo(tokens.filterNotNull()), SyntaxData.WhileNotZero(popped.syntaxes + endWhile)))
                }
                TokenData.Read -> syntaxes.add(Syntax(syntaxInfo, SyntaxData.Read))
                TokenData.Write -> syntaxes.add(Syntax(syntaxInfo, SyntaxData.Write))
                is TokenData.Repeatable -> {
                    awaitingTokens.add(token)
                    diff += token.data.count
                }
            }
            lastToken = token
        }
        return BrainfuckCode(SyntaxData.Root(syntaxes))
    }

    private fun createRepeatSyntax(awaitingTokens: List<Token>, diff: Int): Syntax {
        val token = awaitingTokens.first()
        val syntaxInfo = SyntaxInfo(awaitingTokens)
        return when (token.data) {
            TokenData.Plus, TokenData.Minus -> {
                Syntax(syntaxInfo, SyntaxData.ChangeValue(diff))
            }
            TokenData.MoveRight, TokenData.MoveLeft -> {
                Syntax(syntaxInfo, SyntaxData.Move(diff))
            }
            else -> error("createRepeatSyntax should only be used for repeatable tokens: $awaitingTokens $diff")
        }
    }

}
