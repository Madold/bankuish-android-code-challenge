package com.markusw.bankuishchallenge.main.presentation

sealed interface MainViewModelEvent {

    data class RepositoriesLoadFailed(val reason: String): MainViewModelEvent
    data class LoadNextPageFailed(val reason: String): MainViewModelEvent

}