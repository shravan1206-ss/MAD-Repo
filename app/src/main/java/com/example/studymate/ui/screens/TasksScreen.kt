package com.example.studymate.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.studymate.ui.theme.StudyMateTheme

@Composable
fun TasksScreen() {
    FeatureDetailScreen(
        title = "Tasks & Goals",
        description = "Stay on top of assignments, homework, and deadlines.",
        features = listOf(
            "• Add tasks with priority",
            "• Daily, weekly reminder tasks",
            "• Mark tasks as completed"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TasksScreenPreview() {
    StudyMateTheme {
        TasksScreen()
    }
}