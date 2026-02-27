package com.example.scoreup.features.ranking.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scoreup.features.ranking.domain.entities.RankingUser
import com.example.scoreup.features.ranking.presentation.viewmodels.RankingViewModel

@Composable
fun RankingScreen(
    viewModel: RankingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Ranking",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF4BFFAB))
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    val top3 = uiState.ranking.filter { it.posicion <= 3 }.sortedBy { it.posicion }
                    if (top3.size >= 3) {
                        item {
                            PodiumSection(top3)
                        }
                    }
                    val restOfRanking = uiState.ranking.filter { it.posicion > 0 } // Incluimos todos para ver el diseño
                    items(restOfRanking) { user ->
                        RankingItem(user)
                    }
                }
            }
        }
    }
}

@Composable
fun PodiumSection(topUsers: List<RankingUser>) {
    val first = topUsers.find { it.posicion == 1 }
    val second = topUsers.find { it.posicion == 2 }
    val third = topUsers.find { it.posicion == 3 }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        second?.let { PodiumBar(it, Modifier.weight(1f), height = 140.dp, Color(0xFF1A2B3C)) }
        first?.let { 
            Column(
                modifier = Modifier.weight(1.2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = Color(0xFFFFD700), // Dorado
                    modifier = Modifier.size(32.dp)
                )
                PodiumBar(it, Modifier, height = 180.dp, Color(0xFF4A3419), hasCrown = true)
            }
        }

        third?.let { PodiumBar(it, Modifier.weight(1f), height = 120.dp, Color(0xFF2D1B36)) }
    }
}

@Composable
fun PodiumBar(user: RankingUser, modifier: Modifier, height: androidx.compose.ui.unit.Dp, color: Color, hasCrown: Boolean = false) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Surface(
            modifier = Modifier.size(60.dp),
            shape = CircleShape,
            color = Color(0xFF1E3A5F)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = user.nombre.take(2).uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Text(user.nombre, color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(vertical = 4.dp))
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(height),
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            colors = CardDefaults.cardColors(containerColor = color),
            border = if (hasCrown) androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFF9F4B)) else null
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(user.posicion.toString(), color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text("${user.puntos} pts", color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun RankingItem(user: RankingUser) {
    val backgroundColor = if (user.esUsuarioActual) Color(0xFF00241A) else Color(0xFF0A121D)
    val borderColor = if (user.esUsuarioActual) Color(0xFF4BFFAB) else Color.Transparent

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = if (user.esUsuarioActual) androidx.compose.foundation.BorderStroke(1.dp, borderColor) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "#${user.posicion}",
                color = if (user.esUsuarioActual) Color(0xFF4BFFAB) else Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(30.dp)
            )

            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = Color(0xFF1E3A5F)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = user.nombre.split(" ").take(2).joinToString("") { it.take(1) }.uppercase(),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = user.nombre,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            if (user.tendencia != 0) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (user.tendencia > 0) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                        contentDescription = null,
                        tint = if (user.tendencia > 0) Color(0xFF4BFFAB) else Color(0xFFFF4B4B),
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = Math.abs(user.tendencia * 2).toString(), // Simulación de cambio
                        color = if (user.tendencia > 0) Color(0xFF4BFFAB) else Color(0xFFFF4B4B),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            Text(
                text = "${user.puntos} pts",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
