package net.zomis.brainduck.runner

import net.zomis.brainduck.analyze.BrainfuckRuntime
import net.zomis.brainduck.ast.Syntax

interface BrainfuckListener {

    fun before(syntax: Syntax, runtime: BrainfuckRuntime)
    fun after(syntax: Syntax, runtime: BrainfuckRuntime)

}