package com.example.scoreup.features.ranking.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.scoreup.core.ui.theme.extendedColors
import com.example.scoreup.features.ranking.domain.entities.RankingUser

@Composable
fun PodiumSection(topUsers: List<RankingUser>) {
    val first = topUsers.find { it.posicion == 1 }
    val second = topUsers.find { it.posicion == 2 }
    val third = topUsers.find { it.posicion == 3 }
    val extended = MaterialTheme.extendedColors

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        // Segundo lugar (Izquierda)
        Box(modifier = Modifier.weight(1f).align(Alignment.Bottom)) {
            second?.let { PodiumBar(it, Modifier, height = 130.dp, extended.podiumSecond) }
        }

        // Primer lugar (Centro) - Más alto
        first?.let { 
            Column(
                modifier = Modifier.weight(1.2f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = extended.accentGold,
                    modifier = Modifier.size(36.dp)
                )
                PodiumBar(it, Modifier, height = 190.dp, extended.podiumFirst, hasCrown = true)
            }
        }

        // Tercer lugar (Derecha)
        Box(modifier = Modifier.weight(1f).align(Alignment.Bottom)) {
            third?.let { PodiumBar(it, Modifier, height = 100.dp, extended.podiumThird) }
        }
    }
}
