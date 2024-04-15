import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FrameWindowScope.App() {
    MaterialTheme {
        MenuBar {
            Menu(text = stringResource(Res.string.file)) {
                Item(
                    text = stringResource(Res.string.quit),
                    shortcut = KeyShortcut(Key.F4, alt = true),
                    onClick = {}
                )
            }
            Menu(text = stringResource(Res.string.help)) {
                Item(
                    text = stringResource(Res.string.about),
                    onClick = {
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
fun main() = application {
    Window(
        title = stringResource(Res.string.quit),
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
