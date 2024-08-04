package com.markusw.bankuishchallenge.main.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.markusw.bankuishchallenge.core.presentation.ComposeTestTags
import com.markusw.bankuishchallenge.network.domain.model.GithubRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GithubRepoItemTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var onClickMock: (GithubRepository) -> Unit
    private lateinit var repository: GithubRepository

    @Before
    fun setup() {
        onClickMock = mockk<(GithubRepository) -> Unit>(relaxed = true)
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
        )
    }

    @Test
    fun github_Repo_item_should_be_rendered_correctly() {
        composeRule.setContent {
            GithubRepoItem(repository = repository,modifier = Modifier.fillMaxWidth(), onClick = {})
        }

        composeRule
            .onNodeWithTag(
                ComposeTestTags.REPO_ITEM_TITLE,
                useUnmergedTree = true
            )
            .assertExists()

        composeRule.onNodeWithTag(
            ComposeTestTags.REPO_ITEM_AUTHOR_NAME,
            useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun github_repo_onclick_should_be_triggered() {
        composeRule.setContent {
            GithubRepoItem(
                repository = repository,
                onClick = onClickMock
            )
        }

        composeRule
            .onNodeWithTag(ComposeTestTags.REPO_ITEM_CLICKABLE_CONTAINER)
            .performClick()

        verify (exactly = 1) { onClickMock(repository) }
    }

}