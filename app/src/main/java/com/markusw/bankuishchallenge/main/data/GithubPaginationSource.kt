package com.markusw.bankuishchallenge.main.data

import com.markusw.bankuishchallenge.core.utils.Result
import com.markusw.bankuishchallenge.main.domain.PaginationSource
import com.markusw.bankuishchallenge.network.domain.model.GithubRepository
import com.markusw.bankuishchallenge.network.domain.repository.GithubReposRepository

class GithubPaginationSource(
    private val githubReposRepository: GithubReposRepository
) : PaginationSource<Int, GithubRepository> {

    companion object {
        private const val LANGUAGE = "kotlin"
        private const val PAGE_COUNT = 20
    }

    private var currentPage: Int = 2

    override suspend fun loadNextPage(): List<GithubRepository> {
        val result = githubReposRepository.getRepositories(
            language = LANGUAGE,
            count = PAGE_COUNT,
            page = currentPage
        )
        val repositories = when (result) {
            is Result.Error -> {
                //TODO: Make an error handling when API Rate is Exceeded
                emptyList()
            }
            is Result.Success -> {
                currentPage++
                result.data ?: emptyList()
            }
        }

        return repositories
    }

}