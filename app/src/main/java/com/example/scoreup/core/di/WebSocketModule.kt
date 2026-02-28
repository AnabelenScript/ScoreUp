package com.example.scoreup.core.network.websocket.di

import com.example.scoreup.core.network.websocket.data.WebSocketClientImpl
import com.example.scoreup.core.network.websocket.domain.WebSocketClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WebSocketModule {

    @Binds
    abstract fun bindWebSocketClient(
        impl: WebSocketClientImpl
    ): WebSocketClient
}