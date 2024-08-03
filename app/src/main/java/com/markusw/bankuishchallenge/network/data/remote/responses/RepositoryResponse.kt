package com.markusw.bankuishchallenge.network.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class RepositoryResponse(
    val id: Int,
    val name: String,
    val full_name: String,
    val owner: OwnerResponse
)
