import com.google.common.truth.Truth
import net.zomis.brainduck.Brainfuck
import org.junit.Test

class LexerTest {

    @Test
    fun test() {
        val f = Brainfuck.tokenize("+++-<>")
        Truth.assertThat(f.tokens).hasSize(6)
    }

}