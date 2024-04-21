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
}

compose.desktop {
    application {
        mainClass = "de.thomaskuenneth.kotlinconf24.fileassociationdemo.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "FileAssociationDemo"
            packageVersion = version.toString()
        }
    }
}

tasks.withType<AbstractJPackageTask>().all {
    freeArgs.add("--file-associations")
    freeArgs.add(rootProject.file("hello.properties").absolutePath)
}
