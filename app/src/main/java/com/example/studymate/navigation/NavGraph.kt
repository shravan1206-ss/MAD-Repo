package com.example.studymate.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studymate.db.dao.UserDAO
import com.example.studymate.db.modules.DatabaseModule
import com.example.studymate.firebase.AuthViewModel
import com.example.studymate.ui.screens.HomeScreen
import com.example.studymate.ui.screens.LoginScreen
import com.example.studymate.ui.screens.MotivationScreen
import com.example.studymate.ui.screens.SignUpScreen
import com.example.studymate.ui.screens.SplashScreen
import com.example.studymate.ui.screens.StudyTimerScreen
import com.example.studymate.ui.screens.SubjectPlannerScreen
import com.example.studymate.ui.viewmodel.StudyTimerViewModel
import com.example.studymate.ui.viewmodel.StudyTimerViewModelFactory

const val SLIDE_ANIM_DURATION = 350

val smoothEasing = FastOutSlowInEasing

val enterTransitionForward: (@JvmSuppressWildcards AnimatedContentTransitionScope<*>.() -> EnterTransition) =
    {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tween(SLIDE_ANIM_DURATION, easing = smoothEasing)
        )
    }
val exitTransitionForward: (@JvmSuppressWildcards AnimatedContentTransitionScope<*>.() -> ExitTransition) =
    {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tween(SLIDE_ANIM_DURATION, easing = smoothEasing)
        )
    }

val enterTransitionBackward: (@JvmSuppressWildcards AnimatedContentTransitionScope<*>.() -> EnterTransition) =
    {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = tween(SLIDE_ANIM_DURATION, easing = smoothEasing)
        )
    }
val exitTransitionBackward: (@JvmSuppressWildcards AnimatedContentTransitionScope<*>.() -> ExitTransition) =
    {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = tween(SLIDE_ANIM_DURATION, easing = smoothEasing)
        )
    }


@Composable
fun NavGraph(
    startDestination: String = "SplashScreen",
    userDao: UserDAO
) {

    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = enterTransitionForward,
        exitTransition = exitTransitionForward,
        popEnterTransition = enterTransitionBackward,
        popExitTransition = exitTransitionBackward
    ) {
        composable(route = "SplashScreen") {
            SplashScreen(navController)
        }

        composable(route = "LoginScreen") {
            LoginScreen(navController, userDao, authViewModel)
        }

        composable(route = "SignUpScreen") {
            SignUpScreen(navController, authViewModel)
        }

        composable(route = "HomeScreen") {
            HomeScreen(
                navController = navController,
                userDao = userDao
            )
        }

        composable("SubjectPlannerScreen") {
            SubjectPlannerScreen(
                navController = navController
            )
        }
        composable("StudyTimerScreen") {

            val context = LocalContext.current
            val dao = DatabaseModule.getDb(context).timerHistoryDao()

            val timerViewModel: StudyTimerViewModel = viewModel(
                factory = StudyTimerViewModelFactory(dao, context)
            )

            StudyTimerScreen(
                viewModel = timerViewModel,
                navController = navController
            )
        }

        composable("MotivationScreen") {
            MotivationScreen(
                navController = navController
            )
        }
    }
}