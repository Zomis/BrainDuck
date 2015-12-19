package net.zomis.brainf.model.input

import net.zomis.brainf.model.BrainfuckOutput

class StringBuilderOutput implements BrainfuckOutput {

    private final StringBuilder output = new StringBuilder()

    @Override
    void write(char value) {
        output.append(value)
    }

    @Override
    String toString() {
        output.toString()
    }

}
