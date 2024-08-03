@file:OptIn(FlowPreview::class)

package com.markusw.bankuishchallenge.main.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.markusw.bankuishchallenge.main.utils.isLastItemVisible
import com.markusw.bankuishchallenge.network.domain.model.GithubRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@Composable
fun GithubReposList(
    repositories: List<GithubRepository>,
    onBottomReached: () -> Unit,
    modifier: Modifier = Modifier
) {

    val scrollState = rememberLazyListState()

    LaunchedEffect(key1 = scrollState) {
        snapshotFlow {
            scrollState.firstVisibleItemIndex
        }.debounce(500).collectLatest {
            if (scrollState.isLastItemVisible()) {
                onBottomReached()
            }
        }
    }

    LazyColumn(
        modifier = modifier,
        state = scrollState
    ) {
        items(repositories) { repository ->
            GithubRepoItem(
                repository = repository,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}