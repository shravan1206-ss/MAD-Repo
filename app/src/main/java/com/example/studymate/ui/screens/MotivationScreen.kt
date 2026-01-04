@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.studymate.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.studymate.network.model.Quote
import com.example.studymate.ui.theme.AppBackground
import com.example.studymate.ui.theme.StudyMateTheme
import com.example.studymate.ui.viewmodel.MotivationViewModel


@Composable
fun MotivationScreen(
    navController: NavController
) {

    val viewModel: MotivationViewModel = viewModel()
    val quotes by viewModel.quotes.collectAsState()
    val loading by viewModel.loading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadQuotes()
    }

    MotivationScreenContent(
        loading = loading,
        quotes = quotes,
        navController = navController
    )
}


@Composable
fun MotivationScreenContent(
    loading: Boolean,
    quotes: List<Quote>,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Motivation \uD83D\uDCA1") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                modifier = Modifier.shadow(8.dp),
            )
        }
    ) { padding ->

        AppBackground {
            if (loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(quotes) { quote ->
                        MotivationCard(quote)
                    }
                }
            }
        }
    }
}

@Composable
fun MotivationCard(quote: Quote) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
                    containerColor = Color.White
                  )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "“${quote.quote}”",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "- ${quote.author}",
                fontSize = 13.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MotivationScreenPreview() {
    StudyMateTheme {
        MotivationScreenContent(
            navController = rememberNavController(),
            loading = false,
            quotes = listOf(
                Quote(
                    id = 1,
                    quote = "Discipline is choosing between what you want now and what you want most.",
                    author = "Abraham Lincoln"
                ),
                Quote(
                    id = 2,
                    quote = "Success doesn’t come from what you do occasionally.",
                    author = "Marie Forleo"
                )
            )
        )
    }
}
