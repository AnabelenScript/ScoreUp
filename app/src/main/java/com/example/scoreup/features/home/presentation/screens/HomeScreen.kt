package com.example.scoreup.features.home.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.example.scoreup.features.home.presentation.components.ChallengeItem
import com.example.scoreup.features.home.presentation.components.StatCard
import com.example.scoreup.features.home.presentation.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    onChallengeClick: (Int) -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
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
                            Text(
                                text = uiState.userName.take(2).uppercase(),
                                color = colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Bienvenido de nuevo", color = colorScheme.onSurfaceVariant, fontSize = 14.sp)
                        Text(
                            text = if (uiState.userName.isBlank()) "Usuario" else uiState.userName,
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
                    StatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.StarBorder,
                        value = uiState.points.toString(),
                        label = "Puntos",
                        iconColor = colorScheme.error
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.EmojiEvents,
                        value = "#${uiState.ranking}",
                        label = "Ranking",
                        iconColor = colorScheme.tertiary
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.History,
                        value = uiState.seniority,
                        label = "Antigüedad",
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
                    ChallengeItem(
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
                    ChallengeItem(
                        challenge = challenge,
                        onClick = { onChallengeClick(challenge.idReto) }
                    )
                }
            }
        }
    }
}
