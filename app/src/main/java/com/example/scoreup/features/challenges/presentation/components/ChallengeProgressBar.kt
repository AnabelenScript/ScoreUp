package com.example.scoreup.features.challenges.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scoreup.core.ui.theme.extendedColors

@Composable
fun ChallengeProgressBar(
    progress: Int,
    goal: Int,
    fraction: Float,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    val extended = MaterialTheme.extendedColors

    Column(modifier = modifier) {
        Text(
            text = "Progreso",
            color = colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                shape = CircleShape,
                color = extended.progressTrack
            ) {}
            Surface(
                modifier = Modifier
                    .fillMaxWidth(fraction = fraction.coerceIn(0f, 1f))
                    .height(10.dp),
                shape = CircleShape,
                color = colorScheme.tertiary
            ) {}
            Text(
                text = "$progress/$goal",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(top = 14.dp),
                color = colorScheme.onSurfaceVariant,
                fontSize = 13.sp
            )
        }
    }
}
