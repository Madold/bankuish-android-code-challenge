package com.markusw.bankuishchallenge.network.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class RepositoryResponse(
    val id: Int,
    val name: String,
    val full_name: String,
    val owner: OwnerResponse,
    val description: String?,
    val watchers_count: Int,
    val forks_count: Int,
    val open_issues_count: Int,
    val topics: List<String>,
    val visibility: String,
    val default_branch: String,
    val homepage: String?,
    val stargazers_count: Int,
    val language: String?
)
