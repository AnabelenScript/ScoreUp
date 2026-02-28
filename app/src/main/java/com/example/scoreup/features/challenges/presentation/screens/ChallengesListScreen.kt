package com.example.scoreup.features.challenges.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.scoreup.features.challenges.presentation.components.ChallengeAchievementCard
import com.example.scoreup.features.challenges.presentation.components.ChallengeListItem
import com.example.scoreup.features.challenges.presentation.components.ChallengeStatCard
import com.example.scoreup.features.challenges.presentation.viewmodels.ChallengesListViewModel

@Composable
fun ChallengesListScreen(
    onChallengeClick: (Int) -> Unit = {},
    viewModel: ChallengesListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val colorScheme = MaterialTheme.colorScheme
    val extended = MaterialTheme.extendedColors

    Scaffold(
        containerColor = colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(50.dp),
                        shape = CircleShape,
                        color = colorScheme.primaryContainer
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("CM", color = colorScheme.onPrimaryContainer, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Bienvenido de nuevo", color = colorScheme.onSurfaceVariant, fontSize = 14.sp)
                        Text(
                            "Carlos Molina Mendoza",
                            color = colorScheme.onSurface,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ChallengeStatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.StarBorder,
                        value = "185",
                        label = "Puntos",
                        iconColor = colorScheme.error
                    )
                    ChallengeStatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.EmojiEvents,
                        value = "#4",
                        label = "Ranking",
                        iconColor = colorScheme.tertiary
                    )
                    ChallengeStatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.LocalFireDepartment,
                        value = "7",
                        label = "Días activo",
                        iconColor = extended.accentOrange
                    )
                }
            }
            val activeChallenges = uiState.challenges.filter { it.fechaLimite != "Completado" }
            if (activeChallenges.isNotEmpty()) {
                item {
                    Text(
                        "Retos activos (${activeChallenges.size})",
                        color = colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                items(activeChallenges) { challenge ->
                    ChallengeListItem(
                        challenge = challenge,
                        onClick = { onChallengeClick(challenge.idReto) }
                    )
                }
            }

            val completedChallenges = uiState.challenges.filter { it.fechaLimite == "Completado" }
            if (completedChallenges.isNotEmpty()) {
                item {
                    Text(
                        "Completados (${completedChallenges.size})",
                        color = colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                items(completedChallenges) { challenge ->
                    ChallengeListItem(
                        challenge = challenge,
                        onClick = { onChallengeClick(challenge.idReto) }
                    )
                }
            }
            item {
                Text(
                    "Logros recientes",
                    color = colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ChallengeAchievementCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Pets,
                        label = "Primeros pasos",
                        iconColor = colorScheme.tertiary
                    )
                    ChallengeAchievementCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.RocketLaunch,
                        label = "Productivo",
                        iconColor = extended.accentOrange
                    )
                    ChallengeAchievementCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.MenuBook,
                        label = "Buen estudiante",
                        iconColor = colorScheme.primary
                    )
                }
            }
        }
    }
}
