package net.zomis.brainduck.ast

import net.zomis.brainduck.analyze.BrainfuckRuntime

data class Syntax(val info: SyntaxInfo, val data: SyntaxData)

data class SyntaxInfo(val tokens: List<Token>)

sealed interface SyntaxData {
    interface SteppableSyntax {
        val delta: Int
    }

    data object Read : SyntaxData
    data object Write : SyntaxData
    data class Move(override val delta: Int) : SyntaxData, SteppableSyntax
    data class ChangeValue(override val delta: Int) : SyntaxData, SteppableSyntax
    data class WhileNotZero(val children: List<Syntax>) : SyntaxData
    data object EndWhile : SyntaxData
    data object Comment : SyntaxData
    class Advanced(val perform: (BrainfuckRuntime) -> Unit) : SyntaxData
    data class Root(val children: List<Syntax>) : SyntaxData

}