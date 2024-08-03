package com.markusw.bankuishchallenge.details.presentation

sealed interface DetailsViewModelEvents {

    data class RepositoryLoadFailed(val reason: String): DetailsViewModelEvents

}