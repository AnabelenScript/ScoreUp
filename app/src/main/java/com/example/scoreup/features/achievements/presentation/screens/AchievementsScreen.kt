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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scoreup.core.ui.theme.extendedColors
import com.example.scoreup.features.achievements.domain.entities.Achievement
import com.example.scoreup.features.achievements.presentation.viewmodels.AchievementViewModel

@Composable
fun AchievementsScreen(
    viewModel: AchievementViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val colorScheme = MaterialTheme.colorScheme
    val extended = MaterialTheme.extendedColors

    Scaffold(
        containerColor = colorScheme.background
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
                        color = colorScheme.onSurface,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${uiState.totalUnlocked} de ${uiState.totalAchievements} desbloqueados",
                        color = colorScheme.onSurfaceVariant,
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
                        .background(colorScheme.surfaceContainerHighest, CircleShape)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(uiState.totalUnlocked.toFloat() / uiState.totalAchievements)
                            .fillMaxHeight()
                            .background(extended.accentOrange, CircleShape)
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
    val colorScheme = MaterialTheme.colorScheme
    val borderColor = if (achievement.isUnlocked) colorScheme.tertiary else androidx.compose.ui.graphics.Color.Transparent
    val iconColor = if (achievement.isUnlocked) colorScheme.tertiary else colorScheme.onSurfaceVariant

    Card(
        modifier = Modifier
            .aspectRatio(0.85f)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceContainerLowest),
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
                color = if (achievement.isUnlocked) colorScheme.tertiaryContainer else colorScheme.surfaceContainerHigh
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (achievement.isUnlocked) Icons.Default.EmojiEvents else Icons.Default.Lock,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = achievement.name,
                color = colorScheme.onSurface,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Text(
                text = achievement.description,
                color = colorScheme.onSurfaceVariant,
                fontSize = 10.sp,
                modifier = Modifier.padding(top = 4.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))

            val requirementText = buildString {
                if (achievement.requiredPoints > 0) append("${achievement.requiredPoints} pts")
                if (achievement.requiredPoints > 0 && achievement.requiredRetos > 0) append(" · ")
                if (achievement.requiredRetos > 0) append("${achievement.requiredRetos} retos")
            }
            if (requirementText.isNotEmpty()) {
                Surface(
                    color = colorScheme.primaryContainer.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = requirementText,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        color = colorScheme.primary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun StatsSection() {
    val colorScheme = MaterialTheme.colorScheme
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceContainerLow),
        border = androidx.compose.foundation.BorderStroke(1.dp, colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Estadisticas", color = colorScheme.onSurface, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            
            StatRow("Puntos totales", "185")
            StatRow("Retos completados", "1")
            StatRow("Días activo", "7")
        }
    }
}

@Composable
fun StatRow(label: String, value: String) {
    val colorScheme = MaterialTheme.colorScheme
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = colorScheme.onSurfaceVariant, fontSize = 14.sp)
        Text(value, color = colorScheme.onSurface, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}
