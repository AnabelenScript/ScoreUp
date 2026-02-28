package com.example.scoreup.features.challenges.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scoreup.features.home.domain.entities.Challenge

@Composable
fun CompletedChallengeContent(
    challenge: Challenge,
    progress: Int
) {
    val colorScheme = MaterialTheme.colorScheme
    val goal = challenge.meta

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressChart(
            progress = 1f,
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "$progress de $goal",
            color = colorScheme.onSurfaceVariant,
            fontSize = 15.sp
        )
    }

    Spacer(modifier = Modifier.height(28.dp))

    ChallengeProgressBar(
        progress = progress,
        goal = goal,
        fraction = 1f
    )

    Spacer(modifier = Modifier.height(32.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.tertiary.copy(alpha = 0.1f)
        ),
        border = BorderStroke(1.dp, colorScheme.tertiary.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "¡Reto completado!",
                color = colorScheme.tertiary,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Has alcanzado la meta de $goal. ¡Felicidades por tu esfuerzo!",
                color = colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
