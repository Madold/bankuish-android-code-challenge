@file:OptIn(ExperimentalMaterial3Api::class)

package com.markusw.bankuishchallenge.main.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.markusw.bankuishchallenge.core.presentation.Screens
import com.markusw.bankuishchallenge.main.presentation.components.GithubReposList

@Composable
fun MainScreen(
    state: MainState,
    onBottomReached: () -> Unit,
    onRefresh: () -> Unit,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Github Kotlin Repos")
                }
            )
        }
    ) { innerPadding ->
        AnimatedContent(
            targetState = state.isLoadingInitialRepositories,
            label = "Animated Loading Content"
        ) { isLoading ->

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                GithubReposList(
                    state = state,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth(),
                    repositories = state.repositories,
                    onBottomReached = onBottomReached,
                    onRepoClick = { repository ->
                        navController.navigate("${Screens.Details.route}/${repository.id}")
                    },
                    onRefresh = onRefresh
                )
            }

        }


    }

}