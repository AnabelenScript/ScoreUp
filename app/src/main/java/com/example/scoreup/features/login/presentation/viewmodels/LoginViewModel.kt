package com.example.scoreup.features.login.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scoreup.features.login.domain.entities.Auth
import com.example.scoreup.features.login.domain.entities.RegisterData
import com.example.scoreup.features.login.domain.usecases.LoginUseCase
import com.example.scoreup.features.login.domain.usecases.RegisterUseCase
import com.example.scoreup.features.login.presentation.screens.UsersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsersUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword) }
    }

    fun onPhoneChange(phone: String) {
        if (phone.isEmpty() || phone.all { it.isDigit() }) {
            _uiState.update { it.copy(phone = phone) }
        }
    }


    fun login() {
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = loginUseCase(
                Auth(
                    email = uiState.value.email,
                    password = uiState.value.password
                )
            )

            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { user ->
                        currentState.copy(
                            isLoading = false,
                            user = user
                        )
                    },
                    onFailure = { error ->
                        currentState.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                )
            }
        }
    }

    fun register() {
        val state = uiState.value

        if (state.password != state.confirmPassword) {
            _uiState.update { it.copy(error = "Las contraseñas no coinciden") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = registerUseCase(
                RegisterData(
                    nombre = state.name,
                    email = state.email,
                    password = state.password,
                    phone = state.phone
                )
            )

            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { user ->
                        currentState.copy(
                            isLoading = false,
                            user = user
                        )
                    },
                    onFailure = { error ->
                        currentState.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                )
            }
        }
    }
}
