package com.markusw.bankuishchallenge.network.domain.remote.services

import com.markusw.bankuishchallenge.network.data.remote.responses.RepositoriesResponse
import com.markusw.bankuishchallenge.network.data.remote.responses.RepositoryResponse

interface GithubService {

    suspend fun getRepositories(
        language: String,
        count: Int,
        page: Int
    ): RepositoriesResponse

    suspend fun getRepository(id: String): RepositoryResponse

}