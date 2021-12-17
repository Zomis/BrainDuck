import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import net.zomis.brainduck.compose.rememberBrainduckViewModel

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val viewModel = rememberBrainduckViewModel()
        App(viewModel)
    }
}