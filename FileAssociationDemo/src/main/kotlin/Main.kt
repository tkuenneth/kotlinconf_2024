package de.thomaskuenneth.kotlinconf24.fileassociationdemo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.*
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
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread


@OptIn(ExperimentalResourceApi::class, ExperimentalComposeUiApi::class)
@Composable
fun App(args: List<String>) {
    val fileNames = remember { args.toMutableStateList() }
    createServerSocket { filename -> fileNames.add(filename) }
    LaunchedEffect(Unit) {
        with(Desktop.getDesktop()) {
            installOpenFileHandler { event ->
                event.files.forEach {
                    fileNames.add(it.absolutePath)
                }
            }
        }
    }
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize()
                .onExternalDrag(
                    onDrop = { externalDragValue ->
                        with(externalDragValue.dragData) {
                            if (this is DragData.FilesList) {
                                fileNames.addAll(readFiles())
                            } else if (this is DragData.Text) {
                                println(this.readText())
                            }
                        }
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (fileNames.isEmpty()) {
                Text(
                    text = stringResource(Res.string.welcome),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn {
                    items(fileNames) { item ->
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
    setAppName("File Association Demo")
    if (!sendFiles(args)) {
        Window(
            title = stringResource(Res.string.app_name),
            icon = painterResource(Res.drawable.logo),
            onCloseRequest = ::exitApplication
        ) {
            App(args.toList())
        }
    }
}

fun sendFiles(args: Array<String>): Boolean {
    return if (args.isNotEmpty()) try {
        val socket = Socket("localhost", 7777)
        with(PrintWriter(socket.getOutputStream(), true)) {
            args.forEach { filename ->
                println(filename)
            }
        }
        socket.close()
        true
    } catch (ignore: Throwable) {
        false
    } else false
}

fun createServerSocket(callback: (String) -> Unit) {
    thread {
        val serverSocket = ServerSocket(7777)
        while (true) {
            val clientSocket = serverSocket.accept()
            val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
            thread {
                reader.readLine()?.let { message ->  callback(message) }
                clientSocket.close()
            }
        }
    }
}
