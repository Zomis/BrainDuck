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
import androidx.compose.ui.unit.dp
import brainduck.composeapp.generated.resources.Res
import brainduck.composeapp.generated.resources.compose_multiplatform
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import net.zomis.brainduck.compose.BrainduckViewModel
import net.zomis.brainduck.compose.MemoryCell
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(viewModel: BrainduckViewModel) {
    MaterialTheme {
        Column(modifier = Modifier.fillMaxWidth().onKeyEvent {
            if (it.type == KeyEventType.KeyUp) {
                println(it.key == Key.F9)
            }
            false
        }, horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth().height(48.dp)) {
                Icon(painterResource(Res.drawable.compose_multiplatform), "", modifier = Modifier.size(48.dp))
                Text("Toolbar")
            }

            val focus = remember { FocusRequester() }
            LaunchedEffect(Unit) {
                focus.requestFocus()
            }
            rememberRichTextState()

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
fun MemoryCellView(memoryCell: MemoryCell) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(memoryCell.address.toString(), modifier = Modifier.fillMaxWidth(0.5f))
        Text(memoryCell.value.value.toString(), modifier = Modifier.fillMaxWidth(0.5f))
    }
}