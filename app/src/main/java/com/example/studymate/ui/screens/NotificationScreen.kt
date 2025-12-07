package com.example.studymate.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.studymate.ui.theme.StudyMateTheme

@Composable
fun NotificationScreen() {
    FeatureDetailScreen(
        title = "Notifications",
        description = "Set reminders to stay consistent.",
        features = listOf(
            "• Daily study reminders",
            "• Subject-specific alerts",
            "• Break reminders during sessions"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    StudyMateTheme {
        NotificationScreen()
    }
}