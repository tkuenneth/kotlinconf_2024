package de.thomaskuenneth.kotlinconf24.menubardemo

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.window.DialogProperties
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AskSaveDialog(
    appWindowState: AppWindowState
) {
    val f = remember { FocusRequester() }
    val callback1: (Char) -> Unit = {
        println("===> enter ${it}")
    }
    val callback2: (Char) -> Unit = {
        println("===> exit ${it}")
    }
    AlertDialog(
        title = { Text(text = stringResource(Res.string.save_changes)) },
        text = { Text(text = stringResource(Res.string.save_changes_message)) },
        onDismissRequest = { appWindowState.cancelClose() },
        dismissButton = {
            TextButtonWithShortcut(
                text = stringResource(Res.string.no),
                onClick = { appWindowState.closeWithoutSave() },
                registerAcceleratorKey = callback1,
                unregisterAcceleratorKey = callback2
            )
        },
        confirmButton = {
            TextButtonWithShortcut(
                text = stringResource(Res.string.yes),
                onClick = { appWindowState.closeAndSave() },
                registerAcceleratorKey = callback1,
                unregisterAcceleratorKey = callback2
            )
        },
        properties = DialogProperties(),
        modifier = Modifier.focusRequester(f)
    )
    LaunchedEffect(Unit) {
        f.requestFocus()
    }
}

@Composable
fun TextButtonWithShortcut(
    text: String,
    onClick: () -> Unit,
    registerAcceleratorKey: (Char) -> Unit,
    unregisterAcceleratorKey: (Char) -> Unit,
) {
    var acceleratorKey by remember { mutableStateOf(Char.MIN_VALUE) }
    val annotatedText = remember(text) {
        val parts = text.split("[", limit = 2)
        buildAnnotatedString {
            append(parts[0])
            if (parts.size == 2) {
                with(parts[1]) {
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append(first().also {
                            acceleratorKey = it
                        })
                    }
                    append(substring(1))
                }
            }
        }
    }
    TextButton(onClick = onClick) {
        Text(text = annotatedText)
    }
    LaunchedEffect(Unit) {
        registerAcceleratorKey(acceleratorKey)
    }

    DisposableEffect(Unit) {
        onDispose {
            unregisterAcceleratorKey(acceleratorKey)
        }
    }
}
