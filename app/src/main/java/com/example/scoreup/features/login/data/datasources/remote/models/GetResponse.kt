package com.example.scoreup.features.login.data.datasources.remote.models

data class GetResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)
