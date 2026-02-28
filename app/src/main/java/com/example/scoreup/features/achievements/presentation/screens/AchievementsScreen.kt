package com.example.scoreup.features.achievements.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scoreup.features.achievements.presentation.components.AchievementCard
import com.example.scoreup.features.achievements.presentation.components.AchievementProgressBar
import com.example.scoreup.features.achievements.presentation.components.AchievementStatsSection
import com.example.scoreup.features.achievements.presentation.viewmodels.AchievementViewModel

@Composable
fun AchievementsScreen(
    viewModel: AchievementViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val colorScheme = MaterialTheme.colorScheme

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
                AchievementProgressBar(
                    unlocked = uiState.totalUnlocked,
                    total = uiState.totalAchievements
                )
            }

            items(uiState.achievements) { achievement ->
                AchievementCard(achievement)
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                AchievementStatsSection(
                    totalPoints = "185",
                    completedChallenges = "1",
                    activeDays = "7"
                )
            }
        }
    }
}
