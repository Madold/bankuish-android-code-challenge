package com.markusw.bankuishchallenge.details.presentation

import com.markusw.bankuishchallenge.network.domain.model.GithubRepository

data class DetailsState(
    val repository: GithubRepository = GithubRepository(),
    val isLoadingRepository: Boolean = false
)
