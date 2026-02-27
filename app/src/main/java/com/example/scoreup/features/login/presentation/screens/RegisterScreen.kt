package com.example.scoreup.features.login.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.scoreup.R
import com.example.scoreup.core.ui.theme.*
import com.example.scoreup.features.login.presentation.viewmodels.AuthViewModel

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.user) {
        if (uiState.user != null) {
            navController.navigate("home") {
                popUpTo("register") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundDark),
        contentAlignment = Alignment.Center
    ) {

        if (uiState.isLoading) {
            CircularProgressIndicator(
                color = BrandGreen
            )
        } else {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo1),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(160.dp)
                        .width(160.dp)
                        .padding(bottom = 32.dp)
                )

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = surfaceDark
                    ),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, BrandBlue),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        Text(
                            text = "Crear cuenta",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = onBackgroundDark,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        // Nombre
                        OutlinedTextField(
                            value = uiState.name,
                            onValueChange = viewModel::onNameChange,
                            label = { Text("Nombre") },
                            placeholder = { Text("Ej. Juan Perez") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = BrandBlue,
                                unfocusedBorderColor = outlineDark,
                                focusedLabelColor = BrandBlue,
                                unfocusedLabelColor = outlineDark,
                                focusedTextColor = onSurfaceDark,
                                unfocusedTextColor = onSurfaceDark,
                                cursorColor = BrandBlue,
                                unfocusedContainerColor = surfaceContainerDark,
                                focusedContainerColor = surfaceContainerDark
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Correo electrónico
                        OutlinedTextField(
                            value = uiState.email,
                            onValueChange = viewModel::onEmailChange,
                            label = { Text("Correo electrónico") },
                            placeholder = { Text("example@example.com") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = BrandBlue,
                                unfocusedBorderColor = outlineDark,
                                focusedLabelColor = BrandBlue,
                                unfocusedLabelColor = outlineDark,
                                focusedTextColor = onSurfaceDark,
                                unfocusedTextColor = onSurfaceDark,
                                cursorColor = BrandBlue,
                                unfocusedContainerColor = surfaceContainerDark,
                                focusedContainerColor = surfaceContainerDark
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Contraseña
                        OutlinedTextField(
                            value = uiState.password,
                            onValueChange = viewModel::onPasswordChange,
                            label = { Text("Contraseña") },
                            placeholder = { Text("Escribe tu contraseña") },
                            singleLine = true,
                            visualTransformation = if (passwordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(
                                    onClick = { passwordVisible = !passwordVisible }
                                ) {}
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = BrandBlue,
                                unfocusedBorderColor = outlineDark,
                                focusedLabelColor = BrandBlue,
                                unfocusedLabelColor = outlineDark,
                                focusedTextColor = onSurfaceDark,
                                unfocusedTextColor = onSurfaceDark,
                                cursorColor = BrandBlue,
                                unfocusedContainerColor = surfaceContainerDark,
                                focusedContainerColor = surfaceContainerDark
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = uiState.confirmPassword,
                            onValueChange = viewModel::onConfirmPasswordChange,
                            label = { Text("Confirma tu contraseña") },
                            placeholder = { Text("Confirma tu contraseña") },
                            singleLine = true,
                            visualTransformation = if (confirmPasswordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(
                                    onClick = { confirmPasswordVisible = !confirmPasswordVisible }
                                ) {}
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = BrandBlue,
                                unfocusedBorderColor = outlineDark,
                                focusedLabelColor = BrandBlue,
                                unfocusedLabelColor = outlineDark,
                                focusedTextColor = onSurfaceDark,
                                unfocusedTextColor = onSurfaceDark,
                                cursorColor = BrandBlue,
                                unfocusedContainerColor = surfaceContainerDark,
                                focusedContainerColor = surfaceContainerDark
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(1.dp))

                        Button(
                            onClick = viewModel::register,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BrandGreen,
                                contentColor = androidx.compose.ui.graphics.Color.Black
                            ),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                        ) {
                            Text(
                                text = "Crear cuenta",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "¿Ya tienes una cuenta? ",
                                fontSize = 13.sp,
                                color = outlineDark
                            )

                            TextButton(
                                onClick = {
                                    navController.navigate("login")
                                },
                                modifier = Modifier
                                    .height(24.dp),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.textButtonColors(
                                    containerColor = androidx.compose.ui.graphics.Color.Transparent,
                                    contentColor = BrandGreen
                                )
                            ) {
                                Text(
                                    text = "Iniciar sesión",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

