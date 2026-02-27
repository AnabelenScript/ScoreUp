package com.example.scoreup.core.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

data class ScoreUpExtendedColors(
    val accentOrange: Color,
    val accentGold: Color,
    val progressTrack: Color,
    val podiumFirst: Color,
    val podiumSecond: Color,
    val podiumThird: Color,
    val podiumFirstBorder: Color
)

val LocalScoreUpExtendedColors = staticCompositionLocalOf {
    ScoreUpExtendedColors(
        accentOrange = Color.Unspecified,
        accentGold = Color.Unspecified,
        progressTrack = Color.Unspecified,
        podiumFirst = Color.Unspecified,
        podiumSecond = Color.Unspecified,
        podiumThird = Color.Unspecified,
        podiumFirstBorder = Color.Unspecified
    )
}

val MaterialTheme.extendedColors: ScoreUpExtendedColors
    @Composable
    @ReadOnlyComposable
    get() = LocalScoreUpExtendedColors.current

private val LightColors = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,

    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,

    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,

    background = backgroundLight,
    onBackground = onBackgroundLight,

    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceContainerLight,
    onSurfaceVariant = onSurfaceVariantLight,

    outline = outlineLight,
    outlineVariant = outlineVariantLight,

    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight
)

private val DarkColors = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,

    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,

    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,

    background = backgroundDark,
    onBackground = onBackgroundDark,

    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,

    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
    surfaceBright = surfaceBrightDark,
    surfaceDim = surfaceDimDark,

    outline = outlineDark,
    outlineVariant = outlineVariantDark,

    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,

    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    scrim = scrimDark
)

private val DarkExtendedColors = ScoreUpExtendedColors(
    accentOrange = AccentOrange,
    accentGold = AccentGold,
    progressTrack = ProgressTrackDark,
    podiumFirst = PodiumFirst,
    podiumSecond = PodiumSecond,
    podiumThird = PodiumThird,
    podiumFirstBorder = PodiumFirstBorder
)

private val LightExtendedColors = ScoreUpExtendedColors(
    accentOrange = AccentOrange,
    accentGold = AccentGold,
    progressTrack = ProgressTrackLight,
    podiumFirst = Color(0xFFFFF3E0),
    podiumSecond = Color(0xFFE3F2FD),
    podiumThird = Color(0xFFF3E5F5),
    podiumFirstBorder = PodiumFirstBorder
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors

    CompositionLocalProvider(LocalScoreUpExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colors,
            typography = AppTypography,
            content = content
        )
    }
}