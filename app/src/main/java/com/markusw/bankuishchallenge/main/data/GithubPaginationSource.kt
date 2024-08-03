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

    override suspend fun loadNextPage(): Result<List<GithubRepository>> {
        val result = githubReposRepository.getRepositories(
            language = LANGUAGE,
            count = PAGE_COUNT,
            page = currentPage
        )
        currentPage++
        return result
    }

    fun getCurrentPage(): Int {
        return currentPage
    }

    fun resetPager() {
        currentPage = 2
    }

}