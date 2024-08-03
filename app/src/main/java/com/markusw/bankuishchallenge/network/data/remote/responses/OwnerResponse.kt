package com.markusw.bankuishchallenge.network.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class OwnerResponse(
    val login: String,
    val avatar_url: String
)
