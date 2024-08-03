@file:OptIn(ExperimentalMaterial3Api::class)

package com.markusw.bankuishchallenge.main.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.markusw.bankuishchallenge.R
import com.markusw.bankuishchallenge.core.presentation.Screens
import com.markusw.bankuishchallenge.main.presentation.components.GithubReposList

@Composable
fun MainScreen(
    state: MainState,
    onBottomReached: () -> Unit,
    onRefresh: () -> Unit,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Github Kotlin Repos")
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
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

                if (state.loadError != null) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.load_error_image),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "An error occurred when loading kotlin repositories",
                            textAlign = TextAlign.Center
                        )
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

}