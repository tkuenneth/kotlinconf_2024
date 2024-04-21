package de.thomaskuenneth.kotlinconf24.fileassociationdemo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.thomaskuenneth.kotlinconf24.fileassociationdemo.fileassociationdemo.generated.resources.Res
import de.thomaskuenneth.kotlinconf24.fileassociationdemo.fileassociationdemo.generated.resources.app_name
import de.thomaskuenneth.kotlinconf24.fileassociationdemo.fileassociationdemo.generated.resources.logo
import de.thomaskuenneth.kotlinconf24.fileassociationdemo.fileassociationdemo.generated.resources.welcome
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import java.awt.Desktop

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(args: List<String>) {
    LaunchedEffect(Unit) {
        with(Desktop.getDesktop()) {
            installOpenFileHandler { event ->
                println(event.searchTerm)
                event.files.forEach {
                    println(it.absolutePath)
                }
            }
        }
    }
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (args.isEmpty()) {
                Text(
                    text = stringResource(Res.string.welcome),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn {
                    items(args) { item ->
                        Text(
                            text = item,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
fun main(args: Array<String>) = application {
    Window(
        title = stringResource(Res.string.app_name),
        icon = painterResource(Res.drawable.logo),
        onCloseRequest = ::exitApplication
    ) {
        App(args.toList())
    }
}
