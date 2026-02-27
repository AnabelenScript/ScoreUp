package com.example.scoreup.features.login.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
fun LoginScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Navegación al tener éxito
    LaunchedEffect(uiState.user) {
        if (uiState.user != null) {
            Log.d("LoginScreen", "Login exitoso, navegando a home")
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    // Mostrar errores vía Toast
    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            Log.e("LoginScreen", "Error de login: $it")
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundDark),
        contentAlignment = Alignment.Center
    ) {
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
                    .height(150.dp)
                    .width(150.dp)
                    .padding(bottom = 32.dp)
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = surfaceDark),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, BrandBlue),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Iniciar sesión",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = onBackgroundDark,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = viewModel::onEmailChange,
                        label = { Text("Correo electrónico") },
                        placeholder = { Text("example@example.com") },
                        singleLine = true,
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
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = uiState.password,
                        onValueChange = viewModel::onPasswordChange,
                        label = { Text("Contraseña") },
                        placeholder = { Text("Escribe tu contraseña") },
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = "Toggle password visibility",
                                    tint = outlineDark
                                )
                            }
                        },
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
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = BrandGreen
                        )
                    } else {
                        Button(
                            onClick = viewModel::login,
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
                                text = "Iniciar sesión",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "¿Aún no tienes una cuenta? ",
                            fontSize = 13.sp,
                            color = outlineDark
                        )
                        TextButton(
                            onClick = { navController.navigate("register") },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Registrarme",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = BrandGreen
                            )
                        }
                    }
                }
            }
        }
    }
}
