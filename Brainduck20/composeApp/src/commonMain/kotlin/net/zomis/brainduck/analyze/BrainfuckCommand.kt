package net.zomis.brainduck.analyze

import net.zomis.brainduck.ast.Syntax
import net.zomis.brainduck.ast.SyntaxData

enum class BrainfuckCommand(val char: Char) {
    Plus('+'),
    Minus('-'),
    Left('<'),
    Right('>'),
    While('['),
    EndWhile(']'),
    Read(','),
    Write('.'),;

    companion object {
        fun from(syntax: Syntax): BrainfuckCommand? {
            return when (syntax.data) {
                is SyntaxData.ChangeValue -> if (syntax.data.delta >= 0) Plus else Minus
                is SyntaxData.Move -> if (syntax.data.delta >= 0) Right else Left
                is SyntaxData.WhileNotZero -> While
                SyntaxData.EndWhile -> EndWhile
                SyntaxData.Read -> Read
                SyntaxData.Write -> Write
                else -> null
            }
        }
    }

}