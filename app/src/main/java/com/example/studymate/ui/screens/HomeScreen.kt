package com.example.studymate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.studymate.db.dao.UserDAO
import com.example.studymate.db.entity.UserEntity
import com.example.studymate.ui.theme.AppBackground
import com.example.studymate.ui.theme.StudyMateTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    userDao: UserDAO
) {

    val scope = rememberCoroutineScope()
    var showLogoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home \uD83C\uDFE1") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                modifier = Modifier.shadow(8.dp),

                actions = {
                    IconButton(onClick = { showLogoutDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.PowerSettingsNew,
                            contentDescription = "Logout",
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        AppBackground {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                item {
                    Text(
                        "Hello, Shravan!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                    Text(
                        "Ready to conquer today's study goals?",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                item {
                    FeatureCard(
                        icon = Icons.Default.School,
                        title = "Subject Planner",
                        description = "Plan what to study and when to study it.",
                        color = Color(0xFF4CAF50)
                    ) {
                        navController.navigate("SubjectPlannerScreen")
                    }
                }

                item {
                    FeatureCard(
                        icon = Icons.Default.Timer,
                        title = "Study Timer",
                        description = "Start a focused session.",
                        color = MaterialTheme.colorScheme.primary
                    ) {
                        navController.navigate("StudyTimerScreen")
                    }
                }


                item {
                    FeatureCard(
                        icon = Icons.Default.Lightbulb,
                        title = "Motivation",
                        description = "Get inspiring study quotes.",
                        color = Color(0xFF673AB7)
                    ) {
                        navController.navigate("MotivationScreen")
                    }
                }
            }
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { },

            title = { Text("Confirm Logout!") },
            text = { Text("Are you sure you want to log out?") },

            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        userDao.clearUser()
                        navController.navigate("LoginScreen") {
                            popUpTo(0)
                        }
                    }
                }) {
                    Text("Logout", color = Color.Red)
                }
            },

            dismissButton = {
                TextButton(onClick = { }) {
                    Text("Cancel", color = MaterialTheme.colorScheme.primary)
                }
            }
        )
    }
}

@Composable
fun FeatureCard(
    icon: ImageVector,
    title: String,
    description: String,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

class FakeUserDao : UserDAO {
    override suspend fun saveUser(user: UserEntity) {
    }

    override suspend fun deleteUser(user: UserEntity) {
    }

    override suspend fun getUser(): UserEntity {
        return UserEntity(email = "user@example.com")
    }

    override suspend fun clearUser() {
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    StudyMateTheme {
        HomeScreen(navController = rememberNavController(), userDao = FakeUserDao())
    }
}
