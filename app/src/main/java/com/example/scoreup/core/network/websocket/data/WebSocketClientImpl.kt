package com.example.scoreup.core.network.websocket.data

import com.example.scoreup.core.network.websocket.domain.WebSocketClient
import com.example.scoreup.core.network.websocket.domain.WebSocketEvent
import com.example.scoreup.core.storage.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketClientImpl @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val tokenManager: TokenManager
) : WebSocketClient {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _events = MutableSharedFlow<WebSocketEvent>(replay = 1)
    override val events: SharedFlow<WebSocketEvent> = _events.asSharedFlow()

    private var webSocket: WebSocket? = null

    override fun connect(url: String) {
        disconnect()

        val token = runBlocking { tokenManager.getToken() }

        val requestBuilder = Request.Builder().url(url)

        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        webSocket = okHttpClient.newWebSocket(requestBuilder.build(), createListener())
    }

    override fun sendMessage(message: String): Boolean {
        return webSocket?.send(message) ?: false
    }

    override fun disconnect(code: Int, reason: String) {
        webSocket?.close(code, reason)
        webSocket = null
    }

    private fun createListener(): WebSocketListener {
        return object : WebSocketListener() {

            override fun onOpen(webSocket: WebSocket, response: Response) {
                scope.launch { _events.emit(WebSocketEvent.Open(response)) }
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                scope.launch { _events.emit(WebSocketEvent.MessageReceived(text)) }
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                scope.launch { _events.emit(WebSocketEvent.Closing(code, reason)) }
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                scope.launch { _events.emit(WebSocketEvent.Closed(code, reason)) }
                this@WebSocketClientImpl.webSocket = null
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                scope.launch { _events.emit(WebSocketEvent.Error(t)) }
                this@WebSocketClientImpl.webSocket = null
            }
        }
    }
}
