package de.thomaskuenneth.kotlinconf24.menubardemo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.Res
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.logo
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.toggle
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.untitled
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalResourceApi::class)
@Composable
fun AppWindow(appWindowState: AppWindowState) {
    val title by appWindowState.title.collectAsState()
    val changed by appWindowState.changed.collectAsState()
    Window(
        title = "${if (changed) "*" else ""}${title.ifEmpty { stringResource(Res.string.untitled) }}",
        icon = painterResource(Res.drawable.logo),
        onCloseRequest = { appWindowState.close() }
    ) {
        AppMenuBar(appWindowState.appState)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = { appWindowState.toggleChanged() }) {
                Text(text = stringResource(Res.string.toggle))
            }
        }
    }
}
