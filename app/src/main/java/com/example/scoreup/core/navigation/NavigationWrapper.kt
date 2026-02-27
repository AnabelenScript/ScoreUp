package com.example.scoreup.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.scoreup.core.ui.components.ScoreUpBottomBar

@Composable
fun NavigationWrapper(
    navGraphs: Set<FeatureNavGraph>
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val noBottomBarRoutes = listOf("login", "register")

    Scaffold(
        bottomBar = {
            if (currentRoute !in noBottomBarRoutes && currentRoute != null) {
                ScoreUpBottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            navGraphs.forEach { graph ->
                graph.registerGraph(this, navController)
            }
        }
    }
}
