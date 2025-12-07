package com.example.studymate.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

@Composable
fun AppBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        StudyLavender,
                        StudyPurple,
                        StudyBlue
                    ),
                    start = Offset.Zero,
                    end = Offset(1200f, 2000f) // premium diagonal
                )
            )
    ) {
        content()
    }
}
