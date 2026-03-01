package com.example.scoreup.features.challenges.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scoreup.features.challenges.presentation.viewmodels.CreateChallengeViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateChallengeScreen(
    onBackClick: () -> Unit,
    viewModel: CreateChallengeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val colorScheme = MaterialTheme.colorScheme
    
    val subjects = listOf("Matemáticas", "Programación", "Física", "Historia", "Inglés", "Química")
    var showSubjectMenu by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                return utcTimeMillis >= calendar.timeInMillis
            }
        }
    )

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            viewModel.resetSuccess()
            onBackClick()
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                        viewModel.onDeadlineChange(sdf.format(Date(it)))
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Reto", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column {
                Text(
                    "Materia",
                    color = colorScheme.onBackground,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Box {
                    OutlinedTextField(
                        value = uiState.subject,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Selecciona una materia", color = colorScheme.outline) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = colorScheme.outline
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledBorderColor = colorScheme.outline,
                            disabledContainerColor = colorScheme.surfaceContainer,
                            disabledTextColor = colorScheme.onSurface,
                            disabledPlaceholderColor = colorScheme.outline
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Box(modifier = Modifier.matchParentSize().clickable { showSubjectMenu = true })
                    DropdownMenu(
                        expanded = showSubjectMenu,
                        onDismissRequest = { showSubjectMenu = false },
                        modifier = Modifier.fillMaxWidth(0.9f).background(colorScheme.surfaceContainer)
                    ) {
                        subjects.forEach { subject ->
                            DropdownMenuItem(
                                text = { Text(subject, color = colorScheme.onSurface) },
                                onClick = {
                                    viewModel.onSubjectChange(subject)
                                    showSubjectMenu = false
                                }
                            )
                        }
                    }
                }
            }

            Column {
                Text(
                    "Descripción del reto",
                    color = colorScheme.onBackground,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = uiState.description,
                    onValueChange = viewModel::onDescriptionChange,
                    placeholder = { Text("Ej. Resolver 20 ejercicios de cálculo", color = colorScheme.outline) },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorScheme.primary,
                        unfocusedBorderColor = colorScheme.outline,
                        focusedContainerColor = colorScheme.surfaceContainer,
                        unfocusedContainerColor = colorScheme.surfaceContainer,
                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                // Meta numérica
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Meta numérica",
                        color = colorScheme.onBackground,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = uiState.goal,
                        onValueChange = viewModel::onGoalChange,
                        placeholder = { Text("Ej. 20", color = colorScheme.outline) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorScheme.primary,
                            unfocusedBorderColor = colorScheme.outline,
                            focusedContainerColor = colorScheme.surfaceContainer,
                            unfocusedContainerColor = colorScheme.surfaceContainer,
                            focusedTextColor = colorScheme.onSurface,
                            unfocusedTextColor = colorScheme.onSurface
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Puntos otorgados",
                        color = colorScheme.onBackground,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = uiState.points,
                        onValueChange = viewModel::onPointsChange,
                        placeholder = { Text("Ej. 20", color = colorScheme.outline) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorScheme.primary,
                            unfocusedBorderColor = colorScheme.outline,
                            focusedContainerColor = colorScheme.surfaceContainer,
                            unfocusedContainerColor = colorScheme.surfaceContainer,
                            focusedTextColor = colorScheme.onSurface,
                            unfocusedTextColor = colorScheme.onSurface
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            Column {
                Text(
                    "Fecha límite",
                    color = colorScheme.onBackground,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Box {
                    OutlinedTextField(
                        value = uiState.deadline,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("YYYY-MM-DD", color = colorScheme.outline) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = colorScheme.outline
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledBorderColor = colorScheme.outline,
                            disabledContainerColor = colorScheme.surfaceContainer,
                            disabledTextColor = colorScheme.onSurface,
                            disabledPlaceholderColor = colorScheme.outline
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Box(modifier = Modifier.matchParentSize().clickable { showDatePicker = true })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.error != null) {
                Text(
                    text = uiState.error!!,
                    color = colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = colorScheme.tertiary
                )
            } else {
                Button(
                    onClick = viewModel::createChallenge,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.tertiary,
                        contentColor = colorScheme.onTertiary
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Guardar reto", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
