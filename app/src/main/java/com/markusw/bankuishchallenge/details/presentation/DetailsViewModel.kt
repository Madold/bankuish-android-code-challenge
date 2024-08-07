package com.markusw.bankuishchallenge.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markusw.bankuishchallenge.DispatcherProvider
import com.markusw.bankuishchallenge.core.utils.Result
import com.markusw.bankuishchallenge.network.domain.model.GithubRepository
import com.markusw.bankuishchallenge.network.domain.repository.GithubReposRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val githubReposRepository: GithubReposRepository,
    private val handle: SavedStateHandle,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    companion object {
        const val REPO_ID = "repoId"
    }

    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()
    private val channel = Channel<DetailsViewModelEvents>()
    val events = channel.receiveAsFlow()

    init {
        loadRepository()
    }

    fun loadRepository() {
        val repoId = handle.get<String>(REPO_ID) ?: ""
        _state.update {
            it.copy(
                isLoadingRepository = true,
                loadError = null
            )
        }

        viewModelScope.launch(dispatcherProvider.io) {
            when (val result = githubReposRepository.getRepository(repoId)) {
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            loadError = result.message,
                            isLoadingRepository = false
                        )
                    }
                    channel.send(
                        DetailsViewModelEvents.RepositoryLoadFailed(
                            reason = result.message ?: "Unknown Error"
                        )
                    )
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            repository = result.data ?: GithubRepository(),
                            isLoadingRepository = false
                        )
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        channel.close()
    }

}