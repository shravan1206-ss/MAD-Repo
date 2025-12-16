package com.example.studymate.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.studymate.ui.theme.AppBackground
import com.example.studymate.ui.theme.StudyMateTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
) {

    LaunchedEffect(Unit) {
        delay(1200)
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    var start by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { start = true }

    val scale by animateFloatAsState(
        targetValue = if (start) 1f else 0.6f,
        animationSpec = tween(700)
    )

    val alpha by animateFloatAsState(
        targetValue = if (start) 1f else 0f,
        animationSpec = tween(700)
    )

    AppBackground {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = androidx.compose.ui.graphics.Color.White,
                    modifier = Modifier
                        .size(90.dp)
                        .scale(scale)
                        .alpha(alpha)
                )

                Text(
                    text = "StudyMate",
                    color = androidx.compose.ui.graphics.Color.White,
                    fontSize = 34.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    StudyMateTheme {
        SplashScreen(
            navController = rememberNavController(),
        )
    }
}
