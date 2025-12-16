package com.example.studymate.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studymate.ui.screens.HomeScreen
import com.example.studymate.ui.screens.LoginScreen
import com.example.studymate.ui.screens.SignUpScreen
import com.example.studymate.ui.screens.SplashScreen

@Composable
fun NavGraph(
    startDestination: String = "splash",
) {

    val navController = rememberNavController()
    val duration = 320


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
            LoginScreen(navController)
        }

        composable(
            "signup",
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            SignUpScreen(navController)
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
            )
        }

    }
}
