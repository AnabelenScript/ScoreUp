package com.example.scoreup.features.achievements.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AchievementStatsSection(
    totalPoints: String,
    seniority: String
) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceContainerLow),
        border = BorderStroke(1.dp, colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Estadísticas",
                color = colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            StatRow(label = "Puntos totales", value = totalPoints)
            StatRow(label = "Antigüedad", value = seniority)
        }
    }
}

@Composable
private fun StatRow(label: String, value: String) {
    val colorScheme = MaterialTheme.colorScheme

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
        Text(
            text = value,
            color = colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}
