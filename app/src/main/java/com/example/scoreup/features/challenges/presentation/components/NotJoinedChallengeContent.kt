package com.example.scoreup.features.challenges.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scoreup.features.home.domain.entities.Challenge

@Composable
fun NotJoinedChallengeContent(
    challenge: Challenge,
    isJoining: Boolean,
    onJoin: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Text(
        text = "Al unirte a un reto debes completar sus indicaciones para registrar tu progreso. Por ejemplo:",
        color = colorScheme.onSurfaceVariant,
        fontSize = 15.sp,
        lineHeight = 22.sp
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = challenge.descripcion,
        color = colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
        fontSize = 14.sp,
        lineHeight = 20.sp
    )

    Spacer(modifier = Modifier.height(40.dp))

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
            Button(
                onClick = onJoin,
                enabled = !isJoining,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.tertiary,
                    contentColor = colorScheme.onTertiary
                )
            ) {
                if (isJoining) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = colorScheme.onTertiary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "Unirme al reto",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
