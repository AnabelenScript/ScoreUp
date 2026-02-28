package com.example.scoreup.features.challenges.presentation.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scoreup.features.challenges.presentation.components.CompletedChallengeContent
import com.example.scoreup.features.challenges.presentation.components.JoinedChallengeContent
import com.example.scoreup.features.challenges.presentation.components.NotJoinedChallengeContent
import com.example.scoreup.features.challenges.presentation.viewmodels.ChallengeDetailViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

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

                    when {
                        uiState.isJoined && uiState.completed -> {
                            CompletedChallengeContent(
                                challenge = challenge,
                                progress = uiState.userChallenge?.progress ?: 0
                            )
                        }
                        uiState.isJoined -> {
                            JoinedChallengeContent(
                                challenge = challenge,
                                progress = uiState.userChallenge?.progress ?: 0,
                                progressIncrement = uiState.progressIncrement,
                                isUpdatingProgress = uiState.isUpdatingProgress,
                                onIncrement = { viewModel.incrementProgress() },
                                onDecrement = { viewModel.decrementProgress() },
                                onUpdateProgress = { viewModel.updateProgress() }
                            )
                        }
                        else -> {
                            NotJoinedChallengeContent(
                                challenge = challenge,
                                isJoining = uiState.isJoining,
                                onJoin = { viewModel.joinChallenge() }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

private fun calculateDaysRemaining(deadline: String): Long {
    return try {
        val dateStr = deadline.substringBefore("T")
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val deadlineDate = sdf.parse(dateStr) ?: return 0
        val today = sdf.parse(sdf.format(Date())) ?: return 0
        val diffMillis = deadlineDate.time - today.time
        val days = TimeUnit.MILLISECONDS.toDays(diffMillis)
        maxOf(0, days)
    } catch (_: Exception) {
        0
    }
}
