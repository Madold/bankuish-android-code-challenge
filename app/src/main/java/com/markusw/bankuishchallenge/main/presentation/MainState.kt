package com.markusw.bankuishchallenge.main.presentation

import com.markusw.bankuishchallenge.network.domain.model.GithubRepository

data class MainState(
    val repositories: List<GithubRepository> = emptyList(),
    val isLoadingMoreRepositories: Boolean = false,
    val isLoadingInitialRepositories: Boolean = false,
    val isRefreshing: Boolean = false,
    val loadError: String? = null
)
