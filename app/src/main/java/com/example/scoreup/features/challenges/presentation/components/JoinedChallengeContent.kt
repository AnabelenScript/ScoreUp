package com.example.scoreup.features.challenges.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
fun JoinedChallengeContent(
    challenge: Challenge,
    progress: Int,
    progressIncrement: Int,
    isUpdatingProgress: Boolean,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onUpdateProgress: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val goal = challenge.meta
    val progressFraction = if (goal > 0) progress.toFloat() / goal.toFloat() else 0f
    val remaining = maxOf(0, goal - progress)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressChart(
            progress = progressFraction,
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "$progress de $goal (Faltan $remaining)",
            color = colorScheme.onSurfaceVariant,
            fontSize = 15.sp
        )
    }

    Spacer(modifier = Modifier.height(28.dp))

    ChallengeProgressBar(
        progress = progress,
        goal = goal,
        fraction = progressFraction
    )

    Spacer(modifier = Modifier.height(32.dp))

    RegisterProgressCard(
        progressIncrement = progressIncrement,
        isUpdatingProgress = isUpdatingProgress,
        onIncrement = onIncrement,
        onDecrement = onDecrement,
        onUpdateProgress = onUpdateProgress
    )
}

@Composable
private fun RegisterProgressCard(
    progressIncrement: Int,
    isUpdatingProgress: Boolean,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onUpdateProgress: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceContainerLow),
        border = BorderStroke(1.dp, colorScheme.outline.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registrar avance",
                color = colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onDecrement,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        Icons.Default.Remove,
                        contentDescription = "Disminuir",
                        tint = colorScheme.onSurface
                    )
                }

                Surface(
                    modifier = Modifier
                        .width(120.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = colorScheme.surfaceContainerHighest
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "$progressIncrement",
                            color = colorScheme.onSurface,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                IconButton(
                    onClick = onIncrement,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Aumentar",
                        tint = colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onUpdateProgress,
                enabled = !isUpdatingProgress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.tertiary,
                    contentColor = colorScheme.onTertiary
                )
            ) {
                if (isUpdatingProgress) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = colorScheme.onTertiary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "Registrar avance",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
