import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.desktop.application.tasks.AbstractJPackageTask
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

val properties = Properties()
val file = rootProject.file("src/main/resources/version.properties")
if (file.isFile) {
    InputStreamReader(FileInputStream(file), Charsets.UTF_8).use { reader ->
        properties.load(reader)
    }
} else error("${file.absolutePath} not found")
version = properties.getProperty("VERSION")
group = "de.thomaskuenneth.kotlinconf24.fileassociationdemo"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.components.resources)
    implementation(compose.material3)
}

compose.desktop {
    application {
        mainClass = "de.thomaskuenneth.kotlinconf24.fileassociationdemo.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "FileAssociationDemo"
            description = "This app showcases file associations"
            copyright = "© 2024 Thomas K\u00fcnneth. All rights reserved."
            vendor = "Thomas K\u00fcnneth"
            packageVersion = version.toString()
            linux {
                jvmArgs("--add-opens", "java.desktop/sun.awt.X11=ALL-UNNAMED")
                jvmArgs("--add-opens", "java.desktop/sun.awt.wl=ALL-UNNAMED")
                menuGroup = "Utility"
            }
            macOS {
                infoPlist {
                    extraKeysRawXml = macExtraPlistKeys
                }
            }
        }
    }
}

tasks.withType<AbstractJPackageTask>().all {
    if(name.startsWith("package")) {
        freeArgs.add("--file-associations")
        freeArgs.add(rootProject.file("abc.properties").absolutePath)
    }
}

val macExtraPlistKeys: String
    get() = """
    <key>CFBundleDocumentTypes</key>
    <array>
        <dict>
            <key>CFBundleTypeExtensions</key>
            <array>
                <string>abc</string>
            </array>
            <key>CFBundleTypeName</key>
            <string>abc</string>
            <key>CFBundleTypeMIMETypes</key>
            <array>
                <string>application/octet-stream</string>
            </array>
            <key>CFBundleTypeRole</key>
            <string>Viewer</string>
        </dict>
    </array>
    """
