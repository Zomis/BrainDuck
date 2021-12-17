import androidx.compose.ui.Alignment
import androidx.compose.ui.window.*
import net.zomis.brainduck.compose.rememberBrainduckViewModel

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(position = WindowPosition.Aligned(Alignment.Center)),
        title = "Brainduck",
    ) {
        App(rememberBrainduckViewModel())
    }
}