package com.markusw.bankuishchallenge.main.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.markusw.bankuishchallenge.network.domain.model.GithubRepository
import com.markusw.bankuishchallenge.ui.theme.BankuishAndroidCodeChallengeTheme

@Composable
fun GithubRepoItem(
    repository: GithubRepository,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = repository.fullName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(text = repository.authorName)
        }
    }
}

@Preview(
    showBackground = true,
    name = "Repo Item Preview"
)
@Composable
fun GithubRepoItemPreview(modifier: Modifier = Modifier) {
    BankuishAndroidCodeChallengeTheme {
        GithubRepoItem(
            repository = GithubRepository(
                name = "Kotlin Basics",
                fullName = "Kotlin Basics by Jetbrains",
                authorName = "Jetbrains Inc"
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}