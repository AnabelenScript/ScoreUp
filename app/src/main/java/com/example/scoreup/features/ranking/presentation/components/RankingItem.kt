package com.example.scoreup.features.ranking.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scoreup.features.ranking.domain.entities.RankingUser

@Composable
fun RankingItem(user: RankingUser) {
    val colorScheme = MaterialTheme.colorScheme
    val backgroundColor = if (user.esUsuarioActual) colorScheme.tertiaryContainer else colorScheme.surfaceContainerLowest
    val borderColor = if (user.esUsuarioActual) colorScheme.tertiary else Color.Transparent

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = if (user.esUsuarioActual) BorderStroke(1.dp, borderColor) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "#${user.posicion}",
                color = if (user.esUsuarioActual) colorScheme.tertiary else colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(30.dp)
            )

            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = colorScheme.primaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = user.nombre.split(" ").take(2).joinToString("") { it.take(1) }.uppercase(),
                        color = colorScheme.onPrimaryContainer,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = user.nombre,
                color = colorScheme.onSurface,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            if (user.tendencia != 0) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (user.tendencia > 0) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                        contentDescription = null,
                        tint = if (user.tendencia > 0) colorScheme.tertiary else colorScheme.error,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = Math.abs(user.tendencia * 2).toString(),
                        color = if (user.tendencia > 0) colorScheme.tertiary else colorScheme.error,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            Text(
                text = "${user.puntos} pts",
                color = colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
