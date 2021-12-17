package net.zomis.brainduck

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BFCodeTest {

    @Test
    fun test() {
        // Print 'Hello, World!' asciis as follows:
        // 72 101 108 108 111 44 32 87 111 114 108 100 33
        // 72 101 +7 +0 +3 44 -12 72+15 111 +3 -6 -8 32+1

        val code = """
            +++++ +++++ [-          10 times
              >+++++ ++             10 * 7 = 70
              >+++++ +++++          10 * 10 = 100
              >++++                 10 * 4 = 40
              <<<] 70 100 40
            >++.                      40 72 100 print 'H'
            >+.+++++ ++..+++.         40 72 111 print 'ello'
            >++++.----- ----- --.     32 72 111 print comma and space
            <<+++++ +++++ +++++.      32 87 111 print 'W'
            >.+++.----- -.----- ---.  32 87 100 print 'orld'
            >+.
        """.trimIndent()

        val bfCode = Brainfuck.code(code)
        bfCode.codeIndex = code.indexOf('[')
        bfCode.search(Commands.END_WHILE, 1)
        assertEquals(']', bfCode.char)

        val output = Brainfuck.code(code).run()
        assertEquals("Hello, World!", output)
    }

}
