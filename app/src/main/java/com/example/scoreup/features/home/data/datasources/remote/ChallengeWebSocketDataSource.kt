package com.example.scoreup.features.home.data.datasources.remote

import com.example.scoreup.core.network.websocket.domain.WebSocketClient
import com.example.scoreup.core.network.websocket.domain.WebSocketEvent
import com.example.scoreup.core.storage.TokenManager
import com.example.scoreup.features.home.data.datasources.remote.models.ChallengeDTO
import com.example.scoreup.features.home.data.datasources.remote.models.ChallengesWsResponseDTO
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

/**
 * DataSource remoto que se suscribe al canal "retos" del WebSocket
 * y convierte los mensajes JSON en DTOs.
 *
 * Flujo: WebSocket.onMessage → JSON → ChallengesWsResponseDTO → List<ChallengeDTO>
 */
@Singleton
class ChallengeWebSocketDataSource @Inject constructor(
    private val webSocketClient: WebSocketClient,
    private val tokenManager: TokenManager,
    private val gson: Gson
) {

    companion object {
        private const val WS_BASE_URL = "ws://184.72.233.162:8080/ws"
    }

    /**
     * Flow que emite la lista de [ChallengeDTO] cada vez que el servidor
     * envía una actualización por WebSocket en el canal "retos".
     */
    val challengesFlow: Flow<List<ChallengeDTO>> = webSocketClient.events
        .mapNotNull { event ->
            when (event) {
                is WebSocketEvent.MessageReceived -> {
                    try {
                        val response = gson.fromJson(
                            event.text,
                            ChallengesWsResponseDTO::class.java
                        )
                        response.retos
                    } catch (e: Exception) {
                        null
                    }
                }
                else -> null
            }
        }

    /**
     * Inicia la conexión WebSocket al canal "retos" con el user_id del usuario
     * autenticado.
     */
    fun connect() {
        val userId = runBlocking { tokenManager.getUserId() } ?: return
        val url = "$WS_BASE_URL?role=alumno&user_id=$userId&channel=retos"
        webSocketClient.connect(url)
    }

    /**
     * Cierra la conexión WebSocket.
     */
    fun disconnect() {
        webSocketClient.disconnect()
    }
}
