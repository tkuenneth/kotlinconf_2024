package de.thomaskuenneth.kotlinconf24.menubardemo

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FrameWindowScope.AppMenuBar(appWindowState: AppWindowState) {
    val appState = appWindowState.appState
    MenuBar {
        Menu(text = stringResource(Res.string.file)) {
            Item(
                text = stringResource(Res.string.new),
                shortcut = create(Key.N),
                onClick = { appState.newWindow() }
            )
            Item(
                text = stringResource(Res.string.open),
                shortcut = create(Key.O),
                onClick = { appState.openFileDialog(window) }
            )
            Item(
                text = stringResource(Res.string.close),
                shortcut = create(Key.W),
                onClick = { appState.scope.launch { appWindowState.close() } }
            )
            if (!IS_MACOS) {
                Item(
                    text = stringResource(Res.string.quit),
                    shortcut = KeyShortcut(Key.F4, alt = true),
                    onClick = { appState.exit {} }
                )
            }
        }
        Menu(text = stringResource(Res.string.help)) {
            if (!IS_MACOS) {
                Item(
                    text = stringResource(Res.string.about),
                    onClick = { appState.setShowAboutDialog(true) }
                )
            }
            Item(
                text = stringResource(Res.string.homepage),
                onClick = { appState.visitUs() }
            )
        }
    }
}
