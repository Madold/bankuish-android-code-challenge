package com.markusw.bankuishchallenge.network.domain.model

import com.markusw.bankuishchallenge.network.data.remote.responses.RepositoryResponse

data class GithubRepository(
    val name: String,
    val fullName: String,
    val authorName: String
)

fun RepositoryResponse.toDomainModel() = GithubRepository(
    name = this.name,
    fullName = this.full_name,
    authorName = this.owner.login
)