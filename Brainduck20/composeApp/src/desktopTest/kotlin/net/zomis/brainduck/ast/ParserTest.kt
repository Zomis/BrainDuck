package net.zomis.brainduck.ast

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.test.runTest
import net.zomis.brainduck.Brainfuck
import org.junit.Test

class ParserTest {

    @Test
    fun test() = runTest {
        val tokens = Brainfuck.tokenize("+++++ +++++[->+>++<<]>.>.")
        tokens.tokens.forEach(::println)
        val program = tokens.parse()
        program.syntax.children.forEach(::println)
        program.syntax.children.asFlow().test {
            awaitItem().apply {
                assertThat(this.data).isInstanceOf(SyntaxData.ChangeValue::class.java)
            }
            awaitItem().apply {
                assertThat(this.data).isInstanceOf(SyntaxData.Comment::class.java)
            }
            awaitItem().apply {
                assertThat(this.data).isInstanceOf(SyntaxData.ChangeValue::class.java)
            }
            awaitItem().apply {
                assertThat(this.data).isInstanceOf(SyntaxData.WhileNotZero::class.java)
            }
            awaitItem().apply {
                assertThat(this.data).isInstanceOf(SyntaxData.Move::class.java)
            }
            awaitItem().apply {
                assertThat(this.data).isInstanceOf(SyntaxData.Write::class.java)
            }
            awaitItem().apply {
                assertThat(this.data).isInstanceOf(SyntaxData.Move::class.java)
            }
            awaitItem().apply {
                assertThat(this.data).isInstanceOf(SyntaxData.Write::class.java)
            }
            awaitComplete()
        }
    }

}
