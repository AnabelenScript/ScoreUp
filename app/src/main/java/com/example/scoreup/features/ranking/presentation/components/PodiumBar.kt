package com.example.scoreup.features.ranking.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scoreup.core.ui.theme.extendedColors
import com.example.scoreup.features.ranking.domain.entities.RankingUser

@Composable
fun PodiumBar(user: RankingUser, modifier: Modifier, height: Dp, color: Color, hasCrown: Boolean = false) {
    val colorScheme = MaterialTheme.colorScheme
    val extended = MaterialTheme.extendedColors
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Surface(
            modifier = Modifier.size(60.dp),
            shape = CircleShape,
            color = colorScheme.primaryContainer
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = user.nombre.take(2).uppercase(),
                    color = colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Text(user.nombre, color = colorScheme.onSurfaceVariant, fontSize = 12.sp, modifier = Modifier.padding(vertical = 4.dp))
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(height),
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            colors = CardDefaults.cardColors(containerColor = color),
            border = if (hasCrown) BorderStroke(1.dp, extended.podiumFirstBorder) else null
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(user.posicion.toString(), color = colorScheme.onSurface, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text("${user.puntos} pts", color = colorScheme.onSurfaceVariant, fontSize = 12.sp)
            }
        }
    }
}
