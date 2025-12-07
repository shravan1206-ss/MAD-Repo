package com.example.studymate.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.studymate.ui.theme.StudyMateTheme

@Composable
fun SubjectsScreen() {
    FeatureDetailScreen(
        title = "Subjects",
        description = "Organize all your subjects and notes in one place.",
        features = listOf(
            "• Add/edit/delete subjects",
            "• Attach notes and materials",
            "• Track study progress per subject"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SubjectsScreenPreview() {
    StudyMateTheme {
        SubjectsScreen()
    }
}