package com.example.studymate.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = StudyPrimary,
    secondary = StudySecondary,
    background = StudyLavender,
    surface = Color.White
)

private val DarkColors = darkColorScheme(
    primary = StudyPurple,
    secondary = StudyBlue,
    background = Color(0xFF0D0D0D),
    surface = Color(0xFF1A1A1A)
)

@Composable
fun StudyMateTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
