package de.thomaskuenneth.kotlinconf24.menubardemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.Res
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.about_title
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.app_name
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.logo
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import java.util.ResourceBundle.getBundle

private val VERSION: String = getBundle("version").getString("VERSION")

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AboutDialog(onCloseRequest: () -> Unit) {
    DialogWindow(
        onCloseRequest = onCloseRequest,
        icon = painterResource(Res.drawable.logo),
        resizable = false,
        title = stringResource(Res.string.about_title)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.logo),
                null,
                modifier = Modifier.requiredSize(96.dp)
            )
            Text(
                text = stringResource(Res.string.app_name),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = VERSION
            )
        }
    }
}
