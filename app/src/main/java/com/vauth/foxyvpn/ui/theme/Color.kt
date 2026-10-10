package com.vauth.foxyvpn.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// A restrained sapphire / aqua palette designed to remain legible through glass layers.
val FoxSeed = Color(0xFF3478F6)

val md_theme_light_primary = Color(0xFF1769E8)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFDCE8FF)
val md_theme_light_onPrimaryContainer = Color(0xFF082858)
val md_theme_light_inversePrimary = Color(0xFFADC7FF)
val md_theme_light_secondary = Color(0xFF526783)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFE0EAF8)
val md_theme_light_onSecondaryContainer = Color(0xFF172B45)
val md_theme_light_tertiary = Color(0xFF087F82)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFB9F2EF)
val md_theme_light_onTertiaryContainer = Color(0xFF002F31)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFF3F7FD)
val md_theme_light_onBackground = Color(0xFF17243A)
val md_theme_light_surface = Color(0xEAF9FBFF)
val md_theme_light_onSurface = Color(0xFF17243A)
val md_theme_light_surfaceVariant = Color(0xFFE7EDF7)
val md_theme_light_onSurfaceVariant = Color(0xFF526078)
val md_theme_light_surfaceTint = md_theme_light_primary
val md_theme_light_surfaceContainerLowest = Color(0xEFFFFFFF)
val md_theme_light_surfaceContainerLow = Color(0xDFF8FAFF)
val md_theme_light_surfaceContainer = Color(0xDCEFF4FC)
val md_theme_light_surfaceContainerHigh = Color(0xDDEAF1FC)
val md_theme_light_surfaceContainerHighest = Color(0xE3E2EBF7)
val md_theme_light_surfaceBright = Color(0xFFF9FBFF)
val md_theme_light_surfaceDim = Color(0xFFDCE4F1)
val md_theme_light_outline = Color(0xFF9DAFC8)
val md_theme_light_outlineVariant = Color(0x66A7B8D0)
val md_theme_light_inverseSurface = Color(0xFF25354E)
val md_theme_light_inverseOnSurface = Color(0xFFF1F5FF)
val md_theme_light_scrim = Color(0x66071327)

val md_theme_dark_primary = Color(0xFF9FC0FF)
val md_theme_dark_onPrimary = Color(0xFF082858)
val md_theme_dark_primaryContainer = Color(0xFF174A9A)
val md_theme_dark_onPrimaryContainer = Color(0xFFDCE8FF)
val md_theme_dark_inversePrimary = Color(0xFF1769E8)
val md_theme_dark_secondary = Color(0xFFB6C7E2)
val md_theme_dark_onSecondary = Color(0xFF20334D)
val md_theme_dark_secondaryContainer = Color(0xFF2D405B)
val md_theme_dark_onSecondaryContainer = Color(0xFFDFEAFC)
val md_theme_dark_tertiary = Color(0xFF7DDEDA)
val md_theme_dark_onTertiary = Color(0xFF003738)
val md_theme_dark_tertiaryContainer = Color(0xFF075E61)
val md_theme_dark_onTertiaryContainer = Color(0xFFB9F2EF)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF080E1B)
val md_theme_dark_onBackground = Color(0xFFEAF1FF)
val md_theme_dark_surface = Color(0xE8172235)
val md_theme_dark_onSurface = Color(0xFFEAF1FF)
val md_theme_dark_surfaceVariant = Color(0xFF26364D)
val md_theme_dark_onSurfaceVariant = Color(0xFFB8C5DA)
val md_theme_dark_surfaceTint = md_theme_dark_primary
val md_theme_dark_surfaceContainerLowest = Color(0xE909101D)
val md_theme_dark_surfaceContainerLow = Color(0xD9142032)
val md_theme_dark_surfaceContainer = Color(0xD91A2940)
val md_theme_dark_surfaceContainerHigh = Color(0xDD203149)
val md_theme_dark_surfaceContainerHighest = Color(0xE52A3A53)
val md_theme_dark_surfaceBright = Color(0xFF34455F)
val md_theme_dark_surfaceDim = Color(0xFF080E1B)
val md_theme_dark_outline = Color(0xFF7387A5)
val md_theme_dark_outlineVariant = Color(0x668DA4C4)
val md_theme_dark_inverseSurface = Color(0xFFEAF1FF)
val md_theme_dark_inverseOnSurface = Color(0xFF25354E)
val md_theme_dark_scrim = Color(0x99030912)

data class FoxyStatusColors(
    val connected: Color,
    val connecting: Color,
    val disconnected: Color,
)

val LightStatusColors = FoxyStatusColors(
    connected = Color(0xFF16865A),
    connecting = Color(0xFFB36B10),
    disconnected = Color(0xFF66758D),
)
val DarkStatusColors = FoxyStatusColors(
    connected = Color(0xFF6DE3B0),
    connecting = Color(0xFFFFC66D),
    disconnected = Color(0xFF9BAAC1),
)
val LocalFoxyStatusColors = staticCompositionLocalOf { LightStatusColors }
val ConnectedGreen = LightStatusColors.connected
val ConnectingAmber = LightStatusColors.connecting
