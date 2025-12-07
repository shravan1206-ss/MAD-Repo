package com.example.studymate.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.studymate.ui.theme.StudyMateTheme

@Composable
fun QuotesScreen() {
    FeatureDetailScreen(
        title = "Motivation Quotes",
        description = "Receive inspiring quotes to stay motivated.",
        features = listOf(
            "• Daily motivational popup",
            "• Pull new quotes from API",
            "• Save favorite quotes"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun QuotesScreenPreview() {
    StudyMateTheme {
        QuotesScreen()
    }
}