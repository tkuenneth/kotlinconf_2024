package de.thomaskuenneth.kotlinconf24.menubardemo

import java.util.*

private val osNameLowerCase = System.getProperty("os.name", "").lowercase(Locale.getDefault())

val IS_MACOS = osNameLowerCase.contains("mac os x")
