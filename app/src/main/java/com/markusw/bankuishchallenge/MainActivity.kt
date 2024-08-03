package com.markusw.bankuishchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
            KoinContext {
                val viewModel = koinViewModel<MainViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                BankuishAndroidCodeChallengeTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            MainScreen(state = state)
                        }
                    }
                }
            }
        }
    }
}

