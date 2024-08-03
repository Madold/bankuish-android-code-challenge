package com.markusw.bankuishchallenge.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markusw.bankuishchallenge.core.utils.Result
import com.markusw.bankuishchallenge.main.data.GithubPaginationSource
import com.markusw.bankuishchallenge.network.domain.repository.GithubReposRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val githubReposRepository: GithubReposRepository,
    private val githubPaginationSource: GithubPaginationSource
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        loadInitialRepositories()
    }

    private fun loadInitialRepositories() {
        _state.update {
            it.copy(
                isLoadingInitialRepositories = true
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            val result = githubReposRepository.getRepositories(
                language = "kotlin",
                count = 20,
                page = 1
            )

            when (result) {
                is Result.Error -> {
                    //TODO Handle error
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            repositories = result.data ?: emptyList(),
                            isLoadingInitialRepositories = false
                        )
                    }
                }
            }

        }
    }

    fun onBottomReached() {
        if (!state.value.isLoadingMoreRepositories) {
            loadNextRepositories()
        }
    }

    private fun loadNextRepositories() {
        _state.update {
            it.copy(isLoadingMoreRepositories = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            val newRepositories = githubPaginationSource.loadNextPage()
            _state.update {
                it.copy(
                    isLoadingMoreRepositories = false,
                    repositories = it.repositories + newRepositories
                )
            }
        }

    }

}