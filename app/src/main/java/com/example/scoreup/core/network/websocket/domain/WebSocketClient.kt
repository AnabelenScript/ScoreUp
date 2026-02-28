package com.example.scoreup.core.network.websocket.domain

import kotlinx.coroutines.flow.SharedFlow

interface WebSocketClient {

    val events: SharedFlow<WebSocketEvent>

    /**
     * Abre una conexión WebSocket al [url] indicado.
     * Si ya existe una conexión activa, la cierra antes de abrir una nueva.
     *
     * @param url URL completa del endpoint WebSocket (ej: "ws://host:port/path")
     */
    fun connect(url: String)

    /**
     * Envía un mensaje de texto a través de la conexión WebSocket activa.
     *
     * @param message Texto a enviar (generalmente JSON serializado)
     * @return true si el mensaje fue encolado exitosamente, false si no hay conexión activa
     */
    fun sendMessage(message: String): Boolean

    /**
     * Cierra la conexión WebSocket de forma ordenada.
     *
     * @param code Código de cierre (por defecto 1000 = cierre normal)
     * @param reason Razón del cierre
     */
    fun disconnect(code: Int = 1000, reason: String = "Cliente desconectado")
}
