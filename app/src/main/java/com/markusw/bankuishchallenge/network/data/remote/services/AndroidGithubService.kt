package com.markusw.bankuishchallenge.network.data.remote.services

import com.markusw.bankuishchallenge.network.data.remote.responses.RepositoriesResponse
import com.markusw.bankuishchallenge.network.data.remote.responses.RepositoryResponse
import com.markusw.bankuishchallenge.network.domain.remote.services.GithubService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse

class AndroidGithubService(
    private val ktorClient: HttpClient
): GithubService {

    companion object {
        const val BASE_URL = "https://api.github.com/"
        private const val Q = "q"
        private const val PER_PAGE = "per_page"
        private const val PAGE = "page"
    }

    override suspend fun getRepositories(
        language: String,
        count: Int,
        page: Int
    ): RepositoriesResponse {
        return ktorClient.get {
            url("${BASE_URL}search/repositories")
            parameter(Q, language)
            parameter(PER_PAGE, count)
            parameter(PAGE, page)
        }.body()
    }

    override suspend fun getRepository(id: String): RepositoryResponse {
        return ktorClient.get("${BASE_URL}repositories/$id").body()
    }
}