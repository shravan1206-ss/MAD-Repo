package com.example.studymate.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.studymate.ui.theme.AppBackground
import com.example.studymate.ui.theme.StudyMateTheme
import com.example.studymate.ui.viewmodel.StudyTimerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyTimerScreenContent(
    timeLeft: Long,
    isRunning: Boolean,
    onToggle: () -> Unit,
    onReset: () -> Unit,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("StudyTimer ⏰") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                modifier = Modifier.shadow(8.dp)
            )
        }
    ) { padding ->

        AppBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                CircularTimer(
                    timeLeft = timeLeft,
                    totalTime = 25 * 60 * 1000L
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                    Button(onClick = onToggle) {
                        Text(if (isRunning) "Pause" else "Start")
                    }

                    OutlinedButton(onClick = onReset) {
                        Text("Reset")
                    }
                }
            }
        }
    }
}

@Composable
fun StudyTimerScreen(
    viewModel: StudyTimerViewModel,
    navController: NavController
) {
    StudyTimerScreenContent(
        timeLeft = viewModel.timeLeft,
        isRunning = viewModel.isRunning,
        onToggle = viewModel::toggle,
        onReset = viewModel::reset,
        navController = navController
    )
}

@Composable
fun CircularTimer(
    timeLeft: Long,
    totalTime: Long
) {
    val progress = timeLeft / totalTime.toFloat()

    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            progress = progress,
            strokeWidth = 10.dp,
            modifier = Modifier.size(220.dp)
        )

        val minutes = (timeLeft / 1000) / 60
        val seconds = (timeLeft / 1000) % 60

        Text(
            text = String.format("%02d:%02d", minutes, seconds),
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview(showBackground = true)
@Composable
fun StudyTimerScreenPreview() {
    StudyMateTheme {
        StudyTimerScreenContent(
            timeLeft = 25 * 60 * 1000L,
            isRunning = true,
            onToggle = {},
            onReset = {},
            navController = rememberNavController()
        )
    }
}
