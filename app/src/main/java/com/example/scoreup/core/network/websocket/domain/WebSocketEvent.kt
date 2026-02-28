package com.example.scoreup.core.network.websocket.domain

import okhttp3.Response

sealed class WebSocketEvent {

    data class Open(val response: Response) : WebSocketEvent()

    data class MessageReceived(val text: String) : WebSocketEvent()

    data class Closing(val code: Int, val reason: String) : WebSocketEvent()

    data class Closed(val code: Int, val reason: String) : WebSocketEvent()

    data class Error(val throwable: Throwable) : WebSocketEvent()
}
