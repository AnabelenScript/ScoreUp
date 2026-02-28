package com.example.scoreup.features.home.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.scoreup.core.ui.theme.extendedColors
import com.example.scoreup.features.home.domain.entities.Challenge
import com.example.scoreup.features.home.presentation.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
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
                    StatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.StarBorder,
                        value = "185",
                        label = "Puntos",
                        iconColor = colorScheme.error
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.EmojiEvents,
                        value = "#4",
                        label = "Ranking",
                        iconColor = colorScheme.tertiary
                    )
                    StatCard(
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
                    ChallengeItem(challenge = challenge)
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
                    ChallengeItem(challenge = challenge)
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
                    AchievementCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Pets,
                        label = "Primeros pasos",
                        iconColor = colorScheme.tertiary
                    )
                    AchievementCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.RocketLaunch,
                        label = "Productivo",
                        iconColor = extended.accentOrange
                    )
                    AchievementCard(
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

@Composable
fun StatCard(modifier: Modifier, icon: ImageVector, value: String, label: String, iconColor: Color) {
    val colorScheme = MaterialTheme.colorScheme
    Card(
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceContainerLowest)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = iconColor.copy(alpha = 0.1f),
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, color = colorScheme.onSurface, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(label, color = colorScheme.onSurfaceVariant, fontSize = 12.sp)
        }
    }
}

@Composable
fun AchievementCard(modifier: Modifier, icon: ImageVector, label: String, iconColor: Color) {
    val colorScheme = MaterialTheme.colorScheme
    Card(
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceContainerLowest)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = iconColor.copy(alpha = 0.1f),
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                label,
                color = colorScheme.onSurfaceVariant,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun ChallengeItem(challenge: Challenge) {
    val colorScheme = MaterialTheme.colorScheme
    val extended = MaterialTheme.extendedColors
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceContainerLow),
        border = androidx.compose.foundation.BorderStroke(1.dp, colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = colorScheme.primaryContainer,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = challenge.materia,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        color = colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
                Icon(Icons.Default.ChevronRight, null, tint = colorScheme.onSurfaceVariant)
            }

            Text(challenge.descripcion, color = colorScheme.onSurface, fontSize = 15.sp, fontWeight = FontWeight.Bold)

            Box(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.fillMaxWidth().height(8.dp).background(extended.progressTrack, CircleShape))
                Box(modifier = Modifier.fillMaxWidth(0.4f).height(8.dp).background(colorScheme.tertiary, CircleShape))
                Text(
                    "2/${challenge.meta}",
                    modifier = Modifier.align(Alignment.CenterEnd).offset(y = 12.dp),
                    color = colorScheme.onSurface,
                    fontSize = 11.sp
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                Icon(Icons.Default.Schedule, null, tint = colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(challenge.fechaLimite, color = colorScheme.onSurfaceVariant, fontSize = 12.sp)
            }
        }
    }
}
