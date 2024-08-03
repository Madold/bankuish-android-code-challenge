package com.markusw.bankuishchallenge.network.domain.repository

import com.markusw.bankuishchallenge.network.domain.model.GithubRepository
import com.markusw.bankuishchallenge.core.utils.Result

interface GithubReposRepository {

    suspend fun getRepositories(
        language: String,
        count: Int,
        page: Int
    ): Result<List<GithubRepository>>

    suspend fun getRepository(id: String): Result<GithubRepository>
}