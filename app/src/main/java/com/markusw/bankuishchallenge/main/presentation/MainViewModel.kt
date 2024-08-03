package com.markusw.bankuishchallenge.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markusw.bankuishchallenge.DispatcherProvider
import com.markusw.bankuishchallenge.core.utils.Result
import com.markusw.bankuishchallenge.main.data.GithubPaginationSource
import com.markusw.bankuishchallenge.network.domain.repository.GithubReposRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val githubReposRepository: GithubReposRepository,
    private val githubPaginationSource: GithubPaginationSource,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()
    private val eventsChannel = Channel<MainViewModelEvent>()
    val events = eventsChannel.receiveAsFlow()

    init {
        loadInitialRepositories()
    }

    fun loadInitialRepositories() {
        _state.update {
            it.copy(
                isLoadingInitialRepositories = true,
                loadError = null
            )
        }
        viewModelScope.launch(dispatcherProvider.io) {
            val result = githubReposRepository.getRepositories(
                language = "kotlin",
                count = 20,
                page = 1
            )

            when (result) {
                is Result.Error -> {
                    eventsChannel.send(
                        MainViewModelEvent.RepositoriesLoadFailed(
                            reason = result.message ?: "Unknown Error"
                        )
                    )
                    _state.update {
                        it.copy(loadError = result.message)
                    }
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            repositories = result.data ?: emptyList(),
                        )
                    }
                }
            }

            _state.update {
                it.copy(isLoadingInitialRepositories = false)
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
        viewModelScope.launch(dispatcherProvider.io) {
            when (val result = githubPaginationSource.loadNextPage()) {
                is Result.Error -> {
                    eventsChannel.send(
                        MainViewModelEvent.LoadNextPageFailed(
                            reason = result.message ?: "Unknown Error"
                        )
                    )
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            repositories = it.repositories + (result.data ?: emptyList())
                        )
                    }
                }
            }

            _state.update {
                it.copy(
                    isLoadingMoreRepositories = false
                )
            }
        }

    }

    fun onRefresh() {
        _state.update {
            it.copy(
                isRefreshing = true,
                loadError = null
            )
        }
        viewModelScope.launch(dispatcherProvider.io) {
            githubPaginationSource.resetPager()
            val result = githubReposRepository.getRepositories(
                language = "kotlin",
                count = 20,
                page = 1
            )

            when (result) {
                is Result.Error -> {
                    eventsChannel.send(
                        MainViewModelEvent.RepositoriesLoadFailed(
                            reason = result.message ?: "Unknown Error"
                        )
                    )
                    _state.update {
                        it.copy(
                            loadError = result.message
                        )
                    }
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            repositories = result.data ?: emptyList(),
                        )
                    }
                }
            }

            _state.update {
                it.copy(
                    isRefreshing = false
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        eventsChannel.close()
    }

}