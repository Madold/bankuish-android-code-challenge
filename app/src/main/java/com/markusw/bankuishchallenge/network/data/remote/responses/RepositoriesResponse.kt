package com.markusw.bankuishchallenge.network.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class RepositoriesResponse(
    val total_count: Int,
    val items: List<RepositoryResponse>
)
