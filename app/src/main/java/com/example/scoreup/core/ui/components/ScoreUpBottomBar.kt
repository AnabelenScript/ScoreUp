package com.example.scoreup.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.scoreup.core.ui.theme.BrandGreen

sealed class BottomBarItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Inicio : BottomBarItem("home", "Inicio", Icons.Default.Home)
    object Ranking : BottomBarItem("ranking", "Ranking", Icons.Default.BarChart)
    object Logros : BottomBarItem("logros", "Logros", Icons.Default.EmojiEvents)
    object Crear : BottomBarItem("crear", "Crear", Icons.Default.AddCircleOutline)
}

@Composable
fun ScoreUpBottomBar(navController: NavHostController) {
    val items = listOf(
        BottomBarItem.Inicio,
        BottomBarItem.Ranking,
        BottomBarItem.Logros,
        BottomBarItem.Crear
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = Color(0xFF001220), // Azul oscuro profundo
        contentColor = Color.White
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo("home") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (isSelected) BrandGreen else Color.White
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 12.sp,
                        color = if (isSelected) BrandGreen else Color.White
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
