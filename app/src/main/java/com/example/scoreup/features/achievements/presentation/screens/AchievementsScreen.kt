package com.example.scoreup.features.achievements.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scoreup.features.achievements.domain.entities.Achievement
import com.example.scoreup.features.achievements.presentation.viewmodels.AchievementViewModel

@Composable
fun AchievementsScreen(
    viewModel: AchievementViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text(
                        text = "Logros",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${uiState.totalUnlocked} de ${uiState.totalAchievements} desbloqueados",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(10.dp)
                        .background(Color(0xFF1B1B1B), CircleShape)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(uiState.totalUnlocked.toFloat() / uiState.totalAchievements)
                            .fillMaxHeight()
                            .background(Color(0xFFFF9F4B), CircleShape)
                    )
                }
            }

            items(uiState.achievements) { achievement ->
                AchievementCard(achievement)
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                StatsSection()
            }
        }
    }
}

@Composable
fun AchievementCard(achievement: Achievement) {
    val borderColor = if (achievement.isUnlocked) Color(0xFF4BFFAB) else Color.Transparent
    val iconColor = if (achievement.isUnlocked) Color(0xFF4BFFAB) else Color.Gray
    val icon: ImageVector = when(achievement.iconType) {
        "footprint" -> Icons.Default.Pets
        "lock" -> Icons.Default.Lock
        else -> Icons.Default.EmojiEvents
    }

    Card(
        modifier = Modifier
            .aspectRatio(0.85f)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0A121D)),
        border = if (achievement.isUnlocked) androidx.compose.foundation.BorderStroke(1.dp, borderColor) else null
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = if (achievement.isUnlocked) Color(0xFF00241A) else Color(0xFF1E1E1E)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (achievement.isUnlocked) icon else Icons.Default.Lock,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = achievement.title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = achievement.description,
                color = Color.Gray,
                fontSize = 10.sp,
                modifier = Modifier.padding(top = 4.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                color = Color(0xFF1E3A5F).copy(alpha = 0.4f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = achievement.category,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    color = Color(0xFF4B9FFF),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun StatsSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF001A2C)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E3A5F))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Estadisticas", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            
            StatRow("Puntos totales", "185")
            StatRow("Retos completados", "1")
            StatRow("Días activo", "7")
        }
    }
}

@Composable
fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray, fontSize = 14.sp)
        Text(value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}
