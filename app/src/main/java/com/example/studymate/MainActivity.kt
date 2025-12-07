package com.example.studymate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.studymate.db.modules.DatabaseModule
import com.example.studymate.navigation.NavGraph
import com.example.studymate.ui.theme.AppBackground
import com.example.studymate.ui.theme.StudyMateTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)

        val db = DatabaseModule.getDb(this)
        val userDao = db.userDao()

        setContent {
            StudyMateTheme {
                AppBackground {
                    NavGraph(
                        startDestination = "splash",
                        userDao = userDao
                    )
                }
            }
        }
    }
}

