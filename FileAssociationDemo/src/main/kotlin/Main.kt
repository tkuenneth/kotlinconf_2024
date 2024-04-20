package de.thomaskuenneth.kotlinconf24.fileassociationdemo

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.thomaskuenneth.kotlinconf24.fileassociationdemo.fileassociationdemo.generated.resources.Res
import de.thomaskuenneth.kotlinconf24.fileassociationdemo.fileassociationdemo.generated.resources.app_name
import de.thomaskuenneth.kotlinconf24.fileassociationdemo.fileassociationdemo.generated.resources.logo
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import java.awt.Desktop

@Composable
fun App() {
    LaunchedEffect(Unit) {
        with(Desktop.getDesktop()) {
            setOpenFileHandler { event ->
                println(event.searchTerm)
                event.files.forEach {
                    println(it.absolutePath)
                }
            }
        }
    }
    MaterialTheme {
    }
}

@OptIn(ExperimentalResourceApi::class)
fun main() = application {
    Window(
        title = stringResource(Res.string.app_name),
        icon = painterResource(Res.drawable.logo),
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
