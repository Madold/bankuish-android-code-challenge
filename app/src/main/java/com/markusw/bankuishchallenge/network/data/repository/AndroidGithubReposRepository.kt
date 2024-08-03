package com.markusw.bankuishchallenge.network.data.repository

import com.markusw.bankuishchallenge.core.utils.Result
import com.markusw.bankuishchallenge.network.domain.model.GithubRepository
import com.markusw.bankuishchallenge.network.domain.model.toDomainModel
import com.markusw.bankuishchallenge.network.domain.remote.services.GithubService
import com.markusw.bankuishchallenge.network.domain.repository.GithubReposRepository

class AndroidGithubReposRepository(
    private val githubService: GithubService
) : GithubReposRepository {

    override suspend fun getRepositories(
        language: String,
        count: Int,
        page: Int
    ): Result<List<GithubRepository>> {
        return try {
            val result = githubService
                .getRepositories(language, count, page)
                .items
                .map { it.toDomainModel() }

            Result.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("Error: ${e.message}")
        }
    }

    override suspend fun getRepository(id: String): Result<GithubRepository> {
        return try {
            val result = githubService
                .getRepository(id)
                .toDomainModel()
            Result.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("Error: ${e.message}")
        }
    }

}