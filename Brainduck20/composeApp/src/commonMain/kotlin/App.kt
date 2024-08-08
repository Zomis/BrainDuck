import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.dp
import brainduck.composeapp.generated.resources.*
import brainduck.composeapp.generated.resources.Monospace
import brainduck.composeapp.generated.resources.MonospaceBold
import brainduck.composeapp.generated.resources.Res
import brainduck.composeapp.generated.resources.compose_multiplatform
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import kotlinx.coroutines.launch
import net.zomis.brainduck.Brainfuck
import net.zomis.brainduck.BrainfuckInput
import net.zomis.brainduck.BrainfuckOutput
import net.zomis.brainduck.compose.BrainduckViewModel
import net.zomis.brainduck.compose.MemoryCellViewModel
import net.zomis.brainduck.runner.UntilEnd
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App(viewModel: BrainduckViewModel) {
    var font: FontFamily? by remember {
        mutableStateOf(null)
    }
    LaunchedEffect(Unit) {
        font = FontFamily(
//            Font(Res.font.Monospace, weight = FontWeight.Normal),
//            Font(Res.font.MonospaceBold, weight = FontWeight.Bold),
//            Font(Res.font.MonospaceOblique, weight = FontWeight.Normal, style = FontStyle.Italic),
            Font(identity = "Monospace", data = Res.readBytes("font/Monospace.ttf"), weight = FontWeight.Normal),
            Font(identity = "MonospaceBold", data = Res.readBytes("font/MonospaceBold.ttf"), weight = FontWeight.Bold),
            Font(identity = "MonospaceOblique", data = Res.readBytes("font/MonospaceOblique.ttf"), weight = FontWeight.Normal, style = FontStyle.Italic),
        )
        viewModel.init(font!!)
    }

    MaterialTheme {
        Column(modifier = Modifier.fillMaxWidth().onKeyEvent {
            if (it.type == KeyEventType.KeyUp) {
                when (it.key) {
                    Key.F9 -> {
                        viewModel.coroutineScope.launch {
                            Brainfuck.tokenize(viewModel.editorState.annotatedString.text)
                                .parse()
                                .createProgram()
                                .run(UntilEnd, BrainfuckInput.NoInput, BrainfuckOutput.SystemOut)
                        }
                    }
                }
            }
            false
        }, horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth().height(48.dp)) {
                Icon(painterResource(Res.drawable.compose_multiplatform), "", modifier = Modifier.size(48.dp))
            }

            val focus = remember { FocusRequester() }
            LaunchedEffect(Unit) {
                focus.requestFocus()
            }

            Row(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                LazyColumn(modifier = Modifier.fillMaxWidth(0.3f).fillMaxHeight()) {
                    items(viewModel.memory) {
                        MemoryCellView(it)
                    }
                }
                RichTextEditor(viewModel.editorState, modifier = Modifier.fillMaxSize().focusRequester(focus))
            }

            TextField(value = viewModel.output.value, onValueChange = {}, modifier = Modifier.fillMaxWidth().height(300.dp), readOnly = true)
            TextField(value = viewModel.input.value, onValueChange = { viewModel.input.value = it }, modifier = Modifier.fillMaxWidth().height(300.dp))
        }
    }
}

@Composable
fun MemoryCellView(memoryCell: MemoryCellViewModel) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(memoryCell.address.toString(), modifier = Modifier.fillMaxWidth(0.5f))
        Text(memoryCell.value.value.toString(), modifier = Modifier.fillMaxWidth(0.5f))
    }
}