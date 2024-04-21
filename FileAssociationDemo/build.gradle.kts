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
        mainClass = "FileAssociationDemo"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "FileAssociationDemo"
            description = "This app showcases file associations"
            copyright = "© 2024 Thomas K\u00fcnneth. All rights reserved."
            vendor = "Thomas K\u00fcnneth"
            packageVersion = version.toString()
            linux {
                menuGroup = "tools"
            }
        }
    }
}

tasks.withType<AbstractJPackageTask>().all {
    if(name.startsWith("package")) {
        freeArgs.add("--file-associations")
        freeArgs.add(rootProject.file("hello.properties").absolutePath)
    }
}
