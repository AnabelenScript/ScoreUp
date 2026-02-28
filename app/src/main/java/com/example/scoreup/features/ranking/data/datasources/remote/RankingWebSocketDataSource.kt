package com.example.scoreup.features.ranking.data.datasources.remote

import com.example.scoreup.core.network.websocket.domain.WebSocketClient
import com.example.scoreup.core.network.websocket.domain.WebSocketEvent
import com.example.scoreup.core.storage.TokenManager
import com.example.scoreup.features.ranking.data.datasources.remote.models.RankingResponseDTO
import com.example.scoreup.features.ranking.data.datasources.remote.models.RankingUserDTO
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RankingWebSocketDataSource @Inject constructor(
    private val webSocketClient: WebSocketClient,
    private val tokenManager: TokenManager,
    private val gson: Gson
) {

    companion object {
        private const val WS_BASE_URL = "ws://184.72.233.162:8080/ws"
    }

    val rankingFlow: Flow<List<RankingUserDTO>> = webSocketClient.events
        .mapNotNull { event ->
            when (event) {
                is WebSocketEvent.MessageReceived -> {
                    try {
                        val response = gson.fromJson(
                            event.text,
                            RankingResponseDTO::class.java
                        )
                        response.ranking
                    } catch (e: Exception) {
                        null
                    }
                }
                else -> null
            }
        }

    fun connect() {
        val userId = runBlocking { tokenManager.getUserId() } ?: return
        val url = "$WS_BASE_URL?role=alumno&user_id=$userId&channel=rank"
        webSocketClient.connect(url)
    }

    fun disconnect() {
        webSocketClient.disconnect()
    }
}
