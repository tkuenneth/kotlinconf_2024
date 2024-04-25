package de.thomaskuenneth.kotlinconf24.menubardemo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogWindow
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.Res
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.logo
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.settings_text
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.settings_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SettingsDialog(
    onCloseRequest: () -> Unit
) {
    DialogWindow(
        title = stringResource(Res.string.settings_title),
        icon = painterResource(Res.drawable.logo),
        resizable = false,
        onCloseRequest = onCloseRequest
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(Res.string.settings_text),
                style = MaterialTheme.typography.body1
            )
        }
    }
}
