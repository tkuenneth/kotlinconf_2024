package de.thomaskuenneth.kotlinconf24.menubardemo

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AskSaveDialog(appWindowState: AppWindowState) {
    AlertDialog(
        title = { Text(text = stringResource(Res.string.save_changes)) },
        text = { Text(text = stringResource(Res.string.save_changes_message)) },
        onDismissRequest = { appWindowState.cancelClose() },
        dismissButton = {
            TextButton(
                onClick = { appWindowState.closeWithoutSave() }
            ) {
                Text(text = stringResource(Res.string.no))
            }
        },
        confirmButton = {
            TextButton(
                onClick = { appWindowState.closeAndSave() }
            ) {
                Text(text = stringResource(Res.string.yes))
            }
        },
        properties = DialogProperties()
    )
}
