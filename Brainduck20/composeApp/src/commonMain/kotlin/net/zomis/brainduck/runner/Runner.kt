package net.zomis.brainduck.runner

import net.zomis.brainduck.BrainfuckInput
import net.zomis.brainduck.BrainfuckOutput
import net.zomis.brainduck.BrainfuckProgram

interface Runner {

    fun run(program: BrainfuckProgram, input: BrainfuckInput, output: BrainfuckOutput, listeners: List<BrainfuckListener>)

}