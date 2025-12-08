package com.example.studymate.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studymate.db.dao.UserDAO
import com.example.studymate.firebase.AuthViewModel
import com.example.studymate.ui.screens.HomeScreen
import com.example.studymate.ui.screens.HomeScreen1
import com.example.studymate.ui.screens.LoginScreen
import com.example.studymate.ui.screens.NotificationScreen
import com.example.studymate.ui.screens.QuotesScreen
import com.example.studymate.ui.screens.SignUpScreen
import com.example.studymate.ui.screens.SplashScreen
import com.example.studymate.ui.screens.SubjectsScreen
import com.example.studymate.ui.screens.TasksScreen
import com.example.studymate.ui.screens.TimerScreen

@Composable
fun NavGraph(
    startDestination: String = "splash",
    userDao: UserDAO
) {

    val navController = rememberNavController()
    val duration = 320
    val authViewModel: AuthViewModel = viewModel()


    fun AnimatedContentTransitionScope<*>.enterTransition() = slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        tween(duration)
    ) + scaleIn(initialScale = 1.05f, animationSpec = tween(duration))


    fun AnimatedContentTransitionScope<*>.exitTransition() = slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        tween(duration)
    ) + fadeOut(tween(duration)) + scaleOut(targetScale = 0.95f, animationSpec = tween(duration))


    fun AnimatedContentTransitionScope<*>.popEnterTransition() = slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        tween(duration)
    ) + scaleIn(initialScale = 0.95f, animationSpec = tween(duration)) + fadeIn(tween(duration))


    fun AnimatedContentTransitionScope<*>.popExitTransition() = slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        tween(duration)
    ) + scaleOut(targetScale = 1.05f, animationSpec = tween(duration))



    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(
            "splash",
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            SplashScreen(navController)
        }

        composable(
            "login",
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            LoginScreen(navController, userDao, authViewModel)
        }

        composable(
            "signup",
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            SignUpScreen(navController, authViewModel)
        }

        composable(
            "home",
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            HomeScreen(
                navController = navController,
                userDao = userDao
            )
        }

        composable("home1") { HomeScreen1() }
        composable("timer") { TimerScreen() }
        composable("subjects") { SubjectsScreen() }
        composable("tasks") { TasksScreen() }
        composable("notifications") { NotificationScreen() }
        composable("quotes") { QuotesScreen() }

    }
}
