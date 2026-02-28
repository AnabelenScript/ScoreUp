package com.example.scoreup.features.challenges.presentation.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scoreup.core.ui.theme.extendedColors
import com.example.scoreup.features.challenges.presentation.viewmodels.ChallengeDetailViewModel
import com.example.scoreup.features.home.domain.entities.Challenge
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeDetailScreen(
    onBackClick: () -> Unit,
    viewModel: ChallengeDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Detalle del reto",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.background,
                    titleContentColor = colorScheme.onBackground,
                    navigationIconContentColor = colorScheme.onBackground
                )
            )
        },
        containerColor = colorScheme.background
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = colorScheme.tertiary)
                }
            }

            uiState.error != null && uiState.challenge == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = uiState.error ?: "Error desconocido",
                            color = colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.loadChallengeDetail() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorScheme.tertiary,
                                contentColor = colorScheme.onTertiary
                            )
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
            }

            uiState.challenge != null -> {
                val challenge = uiState.challenge!!

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    // Subject badge
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = colorScheme.tertiary.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = challenge.materia,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                            color = colorScheme.tertiary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }

                    // Title
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = challenge.descripcion,
                        color = colorScheme.onBackground,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 28.sp
                    )

                    // Days remaining + Points
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Schedule,
                            contentDescription = null,
                            tint = colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${calculateDaysRemaining(challenge.fechaLimite)} días restantes",
                            color = colorScheme.onSurfaceVariant,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            Icons.Default.StarBorder,
                            contentDescription = null,
                            tint = colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${challenge.puntosOtorgados} puntos",
                            color = colorScheme.onSurfaceVariant,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    if (uiState.isJoined) {
                        JoinedContent(
                            challenge = challenge,
                            progress = uiState.userChallenge?.progress ?: 0,
                            progressIncrement = uiState.progressIncrement,
                            isUpdatingProgress = uiState.isUpdatingProgress,
                            completed = uiState.completed,
                            onIncrement = { viewModel.incrementProgress() },
                            onDecrement = { viewModel.decrementProgress() },
                            onUpdateProgress = { viewModel.updateProgress() }
                        )
                    } else {
                        NotJoinedContent(
                            challenge = challenge,
                            isJoining = uiState.isJoining,
                            onJoin = { viewModel.joinChallenge() }
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun JoinedContent(
    challenge: Challenge,
    progress: Int,
    progressIncrement: Int,
    isUpdatingProgress: Boolean,
    completed: Boolean,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onUpdateProgress: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val extended = MaterialTheme.extendedColors
    val goal = challenge.meta
    val progressFraction = if (goal > 0) progress.toFloat() / goal.toFloat() else 0f
    val remaining = maxOf(0, goal - progress)

    // Circular Progress
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

    // Linear progress bar
    Column {
        Text(
            text = "Progreso",
            color = colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            // Track
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                shape = CircleShape,
                color = extended.progressTrack
            ) {}
            // Progress
            Surface(
                modifier = Modifier
                    .fillMaxWidth(fraction = progressFraction.coerceIn(0f, 1f))
                    .height(10.dp),
                shape = CircleShape,
                color = colorScheme.tertiary
            ) {}
            // Label
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

    Spacer(modifier = Modifier.height(32.dp))

    // Register progress card
    if (!completed) {
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

                // Increment / Decrement row
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

                // Submit button
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
    } else {
        // Completed state
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
                Text(
                    text = "🎉",
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "¡Reto completado!",
                    color = colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
private fun NotJoinedContent(
    challenge: Challenge,
    isJoining: Boolean,
    onJoin: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    // Description text
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

    // Join button card
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

@Composable
private fun CircularProgressChart(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    val tertiaryColor = colorScheme.tertiary
    val trackColor = colorScheme.surfaceVariant

    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 800),
        label = "progress"
    )

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 14.dp.toPx()
            val radius = (size.minDimension - strokeWidth) / 2
            val topLeft = Offset(
                (size.width - 2 * radius) / 2,
                (size.height - 2 * radius) / 2
            )
            val arcSize = Size(radius * 2, radius * 2)

            // Background track
            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Progress arc
            if (animatedProgress > 0f) {
                drawArc(
                    color = tertiaryColor,
                    startAngle = -90f,
                    sweepAngle = animatedProgress * 360f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }
        }

        Text(
            text = "${(animatedProgress * 100).toInt()}%",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = colorScheme.onBackground
        )
    }
}

private fun calculateDaysRemaining(deadline: String): Long {
    return try {
        val dateStr = deadline.substringBefore("T")
        val deadlineDate = LocalDate.parse(dateStr)
        val today = LocalDate.now()
        val days = ChronoUnit.DAYS.between(today, deadlineDate)
        maxOf(0, days)
    } catch (_: Exception) {
        0
    }
}
