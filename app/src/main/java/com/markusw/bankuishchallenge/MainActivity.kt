package com.markusw.bankuishchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.markusw.bankuishchallenge.core.presentation.Screens
import com.markusw.bankuishchallenge.details.presentation.DetailsScreen
import com.markusw.bankuishchallenge.details.presentation.DetailsViewModel
import com.markusw.bankuishchallenge.main.presentation.MainScreen
import com.markusw.bankuishchallenge.main.presentation.MainViewModel
import com.markusw.bankuishchallenge.ui.theme.BankuishAndroidCodeChallengeTheme
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

                            MainScreen(
                                state = state,
                                onBottomReached = viewModel::onBottomReached,
                                navController = navController
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
                            
                            DetailsScreen(
                                state = state,
                                navController = navController
                            )
                        }

                    }
                }
            }
        }
    }
}

