package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MetroLightColorScheme = lightColorScheme(
    primary = MetroPrimary,
    onPrimary = MetroOnPrimary,
    primaryContainer = MetroPrimaryContainer,
    onPrimaryContainer = MetroOnPrimaryContainer,
    secondary = MetroSecondary,
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = MetroSecondaryContainer,
    onSecondaryContainer = MetroOnSecondaryContainer,
    tertiary = MetroTertiary,
    onTertiary = MetroOnTertiary,
    tertiaryContainer = MetroTertiaryContainer,
    onTertiaryContainer = MetroOnTertiaryContainer,
    background = MetroBackground,
    onBackground = MetroOnBackground,
    surface = MetroSurface,
    onSurface = MetroOnSurface,
    surfaceVariant = MetroSurfaceContainerHighest,
    onSurfaceVariant = MetroOnSurfaceVariant,
    surfaceContainerLowest = MetroSurfaceContainerLowest,
    surfaceContainerLow = MetroSurfaceContainerLow,
    surfaceContainer = MetroSurfaceContainer,
    surfaceContainerHigh = MetroSurfaceContainerHigh,
    surfaceContainerHighest = MetroSurfaceContainerHighest,
    outline = MetroOutline,
    outlineVariant = MetroOutlineVariant,
    error = MetroError,
    onError = MetroOnError,
    errorContainer = MetroErrorContainer
)

private val MetroDarkColorScheme = darkColorScheme(
    primary = Color(0xFFB7C4FF),
    onPrimary = Color(0xFF002685),
    primaryContainer = Color(0xFF0037B0),
    onPrimaryContainer = Color(0xFFDCE1FF),
    secondary = Color(0xFFBEC6E0),
    onSecondary = Color(0xFF283044),
    secondaryContainer = Color(0xFF3E465C),
    onSecondaryContainer = Color(0xFFDAE2FD),
    tertiary = Color(0xFF93CCFF),
    onTertiary = Color(0xFF003353),
    tertiaryContainer = Color(0xFF004B75),
    onTertiaryContainer = Color(0xFFCCE5FF),
    background = Color(0xFF0B131E),
    onBackground = Color(0xFFE2E7F4),
    surface = Color(0xFF0B131E),
    onSurface = Color(0xFFE2E7F4),
    surfaceVariant = Color(0xFF222B3A),
    onSurfaceVariant = Color(0xFFC4C5D7),
    surfaceContainerLowest = Color(0xFF070E17),
    surfaceContainerLow = Color(0xFF0F1826),
    surfaceContainer = Color(0xFF142031),
    surfaceContainerHigh = Color(0xFF1B283D),
    surfaceContainerHighest = Color(0xFF23324B),
    outline = Color(0xFF8E90A2),
    outlineVariant = Color(0xFF434655)
)

@Composable
fun MetroMumbaiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) MetroDarkColorScheme else MetroLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
