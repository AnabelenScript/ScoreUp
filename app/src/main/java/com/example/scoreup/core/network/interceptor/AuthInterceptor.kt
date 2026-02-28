package com.example.scoreup.core.network.interceptor

import com.example.scoreup.core.storage.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    companion object {
        private val EXCLUDED_PATHS = listOf(
            "users/login",
            "users/register"
        )
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val path = originalRequest.url.encodedPath

        // No agregar token a las rutas de login y registro
        val isExcluded = EXCLUDED_PATHS.any { path.contains(it) }

        if (isExcluded) {
            return chain.proceed(originalRequest)
        }

        val token = runBlocking { tokenManager.getToken() }

        val authenticatedRequest = if (!token.isNullOrEmpty()) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(authenticatedRequest)
    }
}
