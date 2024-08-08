package net.zomis.brainduck.compose

import androidx.compose.runtime.*
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class BrainduckViewModel(val editorState: RichTextState) {
    val coroutineScope = CoroutineScope(Dispatchers.Default)
    val input: MutableState<String> = mutableStateOf("")
    val output: State<String> = mutableStateOf("")
    val memory = (0..65535).map { MemoryCellImpl(it) }

    lateinit var font: FontFamily

    fun init(font: FontFamily) {
        this.font = font
        editorState.toggleSpanStyle(SpanStyle(fontFamily = font))
        editorState.setText("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++.+.+..")
    }
}

@Composable
fun rememberBrainduckViewModel(): BrainduckViewModel {
    val state = rememberRichTextState()
    return remember {
        BrainduckViewModel(state)
    }
}

/*
Compose Editor
- step-by-step running
- run to cursor
- step in/out, run next loop, etc.
- show errors and warnings from analysis
- pause/stop code execution
- syntax highlighting + line numbers
- quick feedback loop when editing code! save snapshots of memory at some points?
- mark memory cells with identifiers

Analysis
- detect infinite loops
- requires code to be parsed and processed as an Abstract Syntax Tree
- input code + optional example inputs
- output to JSON, or to compose editor

Other features
- memory cells data generator
- text printer generator (start from 0 or start from some other memory cell state)

$ inline advanced features
- name cells
- assert values / cell names


Simple console run
- code input
- std in (bytes)
- std out (bytes)

*/