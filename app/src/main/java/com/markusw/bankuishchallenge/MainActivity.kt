package com.markusw.bankuishchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.markusw.bankuishchallenge.core.presentation.Screens
import com.markusw.bankuishchallenge.details.presentation.DetailsScreen
import com.markusw.bankuishchallenge.details.presentation.DetailsViewModel
import com.markusw.bankuishchallenge.details.presentation.DetailsViewModelEvents
import com.markusw.bankuishchallenge.main.presentation.MainScreen
import com.markusw.bankuishchallenge.main.presentation.MainViewModel
import com.markusw.bankuishchallenge.main.presentation.MainViewModelEvent
import com.markusw.bankuishchallenge.ui.theme.BankuishAndroidCodeChallengeTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BankuishAndroidCodeChallengeTheme {
                KoinContext {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Screens.Main.route) {
                        composable(route = Screens.Main.route) {
                            val viewModel = koinViewModel<MainViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            val snackBarHostState = remember { SnackbarHostState() }

                            LaunchedEffect(key1 = Unit) {
                                viewModel.events.collectLatest { event ->
                                    when (event) {
                                        is MainViewModelEvent.LoadNextPageFailed -> {
                                            snackBarHostState.showSnackbar(
                                                message = event.reason,
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                        is MainViewModelEvent.RepositoriesLoadFailed -> {
                                            val result = snackBarHostState.showSnackbar(event.reason, actionLabel = "Try again")
                                            when (result) {
                                                SnackbarResult.ActionPerformed -> {
                                                    viewModel.loadInitialRepositories()
                                                }
                                                else -> Unit
                                            }
                                        }
                                    }
                                }
                            }

                            MainScreen(
                                state = state,
                                onBottomReached = viewModel::onBottomReached,
                                onRefresh = viewModel::onRefresh,
                                navController = navController,
                                snackBarHostState = snackBarHostState
                            )
                        }

                        composable(
                            route = "${Screens.Details}/{repoId}",
                            arguments = listOf(
                                navArgument("repoId") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val viewModel = koinViewModel<DetailsViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            val snackBarHostState = remember {
                                SnackbarHostState()
                            }

                            LaunchedEffect(key1 = Unit) {
                                viewModel.events.collectLatest { event ->
                                    when (event) {
                                        is DetailsViewModelEvents.RepositoryLoadFailed -> {
                                            val result =  snackBarHostState.showSnackbar(
                                                message = "Failed to load repository details",
                                                actionLabel = "Retry"
                                            )

                                            when (result) {
                                                SnackbarResult.ActionPerformed -> {
                                                    viewModel.loadRepository()
                                                }
                                                else -> Unit
                                            }

                                        }
                                    }
                                }
                            }
                            
                            DetailsScreen(
                                state = state,
                                navController = navController,
                                snackBarHostState = snackBarHostState
                            )
                        }

                    }
                }
            }
        }
    }
}

