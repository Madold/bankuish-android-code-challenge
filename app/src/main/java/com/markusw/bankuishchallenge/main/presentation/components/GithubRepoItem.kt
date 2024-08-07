package com.markusw.bankuishchallenge.main.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.markusw.bankuishchallenge.core.presentation.ComposeTestTags
import com.markusw.bankuishchallenge.core.presentation.ComposeTestTags.REPO_ITEM_AUTHOR_NAME
import com.markusw.bankuishchallenge.core.presentation.ComposeTestTags.REPO_ITEM_CLICKABLE_CONTAINER
import com.markusw.bankuishchallenge.network.domain.model.GithubRepository
import com.markusw.bankuishchallenge.ui.theme.BankuishAndroidCodeChallengeTheme

@Composable
fun GithubRepoItem(
    repository: GithubRepository,
    onClick: (GithubRepository) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .testTag(REPO_ITEM_CLICKABLE_CONTAINER)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clickable { onClick(repository) }
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = repository.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag(ComposeTestTags.REPO_ITEM_TITLE)
            )
            Text(text = repository.authorName, modifier = Modifier.testTag(REPO_ITEM_AUTHOR_NAME))
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
                name = "Kotlin",
                fullName = "Kotlin Essentials",
                authorName = "Jetbrains",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut tempus non tellus laoreet porta. Sed in fringilla augue, id pulvinar turpis. Nullam at urna suscipit, viverra ligula in, interdum dolor. Vivamus venenatis eget ex ac venenatis. Aliquam volutpat tellus ut pharetra volutpat. Fusce fermentum, nisi eget consequat luctus, sem arcu mattis justo, eget ultrices neque ipsum eget enim.",
                watchersCount = 34,
                forksCount = 12,
                openIssuesCount = 5,
                topics = listOf(
                    "Android",
                    "Kotlin",
                    "Dagger/Hilt",
                    "MVVM",
                    "MVI",
                    "SQL",
                    "Firebase"
                ),
                visibility = "public",
                defaultBranch = "main",
                authorAvatarUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRz8Y130xmRkKyeyTv_p2eEdFkizYp2NWvWaA&s",
                homepageUrl = "https://google.com",
                starsCount = 21,
                language = "Kotlin"
            ),
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        )
    }
}