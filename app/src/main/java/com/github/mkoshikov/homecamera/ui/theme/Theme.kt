package com.github.mkoshikov.homecamera.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.graphics.luminance
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColors(

)

private val LightColorScheme = lightColors(
    primary = BrandColor,
    onPrimary = Color(0xFFFFFFFF)
)

@Composable
fun HomeCameraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val color = colorScheme.surface.toArgb()
            window.statusBarColor = color
            WindowCompat
                .getInsetsController(window, view)
                .isAppearanceLightStatusBars = color.luminance > 0.5f
        }
    }

    MaterialTheme(
        colors = colorScheme,
        typography = Typography,
        content = content
    )
}