package com.example.studymate.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.studymate.firebase.AuthViewModel
import com.example.studymate.ui.theme.AppBackground
import com.example.studymate.ui.theme.StudyMateTheme
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navController: NavController,
    authViewModel: AuthViewModel?
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // UI Colors
    val buttonColor = Color(0xFF6A5AE0)
    val titleColor = Color(0xFF2D2A45)
    val subtitleColor = Color(0xFF7A78A1)

    AppBackground {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = Color.Transparent
        ) { paddingValues ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp)
                        .shadow(6.dp, RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFDFEFE))
                ) {

                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            "Create an Account",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = titleColor
                        )
                        Spacer(Modifier.height(6.dp))
                        Text("Join StudyMate today!", fontSize = 16.sp, color = subtitleColor)
                        Spacer(Modifier.height(30.dp))

                        OutlinedTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            label = { Text("Full Name") },
                            singleLine = true,
                            leadingIcon = { Icon(Icons.Default.Person, null) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = buttonColor,
                                focusedLabelColor = buttonColor
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(18.dp))

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            singleLine = true,
                            leadingIcon = { Icon(Icons.Default.Email, null) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = buttonColor,
                                focusedLabelColor = buttonColor
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(18.dp))

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            leadingIcon = { Icon(Icons.Default.Lock, null) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = buttonColor,
                                focusedLabelColor = buttonColor
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(32.dp))

                        Button(
                            onClick = {
                                if (email.isBlank() || password.isBlank() || fullName.isBlank()) {
                                    scope.launch { snackbarHostState.showSnackbar("All fields are required") }
                                    return@Button
                                }
                                loading = true

                                authViewModel?.signUp(
                                    username = fullName,
                                    email = email,
                                    password = password,
                                    onSuccess = {
                                        loading = false
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Account created successfully!")
                                        }
                                        navController.navigate("login")
                                    },
                                    onError = { error ->
                                        loading = false
                                        scope.launch { snackbarHostState.showSnackbar(error) }
                                    }
                                )
                            },
                            enabled = !loading && authViewModel != null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonColor,
                                contentColor = Color.White
                            )
                        ) {
                            if (loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text("SIGN UP", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(Modifier.height(18.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Already have an account? ", color = subtitleColor)

                            TextButton(
                                onClick = { navController.navigate("login") },
                                enabled = !loading
                            ) {
                                Text("Login", color = buttonColor, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    StudyMateTheme {
        SignUpScreen(navController = rememberNavController(), authViewModel = null)
    }
}
