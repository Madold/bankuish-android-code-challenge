package com.markusw.bankuishchallenge.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markusw.bankuishchallenge.core.utils.Result
import com.markusw.bankuishchallenge.network.domain.model.GithubRepository
import com.markusw.bankuishchallenge.network.domain.repository.GithubReposRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val githubReposRepository: GithubReposRepository,
    handle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

    init {
        val repoId = handle.get<String>("repoId") ?: ""
        _state.update {
            it.copy(isLoadingRepository = true)
        }

        viewModelScope.launch(Dispatchers.IO) {
            when (val result = githubReposRepository.getRepository(repoId)) {
                is Result.Error -> {
                    //TODO: Handle error
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

}