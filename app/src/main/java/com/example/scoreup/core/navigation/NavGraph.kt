package com.example.scoreup.core.navigation

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.compose.runtime.Composable
import com.example.scoreup.features.login.presentation.screens.LoginScreen
import com.example.scoreup.features.login.presentation.screens.RegisterScreen
import com.example.scoreup.features.home.presentation.screens.HomeScreen
import com.example.scoreup.features.challenges.presentation.screens.ChallengeDetailScreen
import com.example.scoreup.features.challenges.presentation.screens.CreateChallengeScreen
import com.example.scoreup.features.ranking.presentation.screens.RankingScreen
import com.example.scoreup.features.achievements.presentation.screens.AchievementsScreen
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

interface FeatureNavGraph {
    fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    )
}

object LoginNavGraph : FeatureNavGraph {
    @OptIn(ExperimentalAnimationApi::class)
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(
            route = "login",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            Log.d("LoginNavGraph", "Renderizando pantalla Login")
            LoginScreen(navController)
        }

        navGraphBuilder.composable(
            route = "register",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            Log.d("LoginNavGraph", "Renderizando pantalla Register")
            RegisterScreen(navController)
        }
    }
}

object HomeNavGraph : FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(
            route = "home",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            Log.d("HomeNavGraph", "Renderizando pantalla Home")
            HomeScreen(
                onChallengeClick = { challengeId ->
                    navController.navigate("challenge_detail/$challengeId")
                }
            )
        }

        navGraphBuilder.composable(
            route = "challenge_detail/{challengeId}",
            arguments = listOf(navArgument("challengeId") { type = NavType.IntType }),
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            Log.d("HomeNavGraph", "Renderizando pantalla ChallengeDetail")
            ChallengeDetailScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

object RankingNavGraph : FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(
            route = "ranking",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            Log.d("RankingNavGraph", "Renderizando pantalla Ranking")
            RankingScreen()
        }
    }
}

object AchievementsNavGraph : FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(
            route = "logros",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            Log.d("AchievementsNavGraph", "Renderizando pantalla Logros")
            AchievementsScreen()
        }
    }
}

object CreateNavGraph : FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(
            route = "crear",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            Log.d("CreateNavGraph", "Renderizando pantalla Crear Reto")
            CreateChallengeScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
