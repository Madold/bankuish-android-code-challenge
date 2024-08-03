@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.markusw.bankuishchallenge.details.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.markusw.bankuishchallenge.R
import com.markusw.bankuishchallenge.details.presentation.components.RepoDetailItem
import com.markusw.bankuishchallenge.details.utils.generateRandomColor
import com.markusw.bankuishchallenge.main.presentation.components.HyperLinkButton
import com.markusw.bankuishchallenge.network.domain.model.GithubRepository

@Composable
fun DetailsScreen(
    state: DetailsState,
    navController: NavController,
    snackBarHostState: SnackbarHostState
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBar(title = {
                Text(
                    text = state.repository.fullName,
                    style = MaterialTheme.typography.titleLarge
                )
            },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                })
        }
    ) { innerPadding ->
        AnimatedContent(
            modifier = Modifier.padding(innerPadding),
            targetState = state.isLoadingRepository,
            label = "Animated Content"
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
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "An error occurred while fetching repository info",
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .data(state.repository.authorAvatarUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.FillWidth
                        )

                        Text(
                            text = "By: ${state.repository.authorName}",
                            style = MaterialTheme.typography.titleMedium
                        )

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            state.repository.topics.forEach { topic ->
                                AssistChip(
                                    onClick = { },
                                    label = { Text(text = topic) },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = generateRandomColor()
                                    )
                                )
                            }
                        }

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            RepoDetailItem(
                                icon = R.drawable.ic_eye,
                                label = "${state.repository.watchersCount} Watchers"
                            )
                            RepoDetailItem(
                                icon = R.drawable.ic_issue,
                                label = "${state.repository.openIssuesCount} Issues"
                            )
                            RepoDetailItem(
                                icon = R.drawable.ic_fork,
                                label = "${state.repository.forksCount} Forks"
                            )
                            RepoDetailItem(
                                icon = R.drawable.ic_star,
                                label = "${state.repository.starsCount} Stars"
                            )

                        }

                        if (state.repository.homepageUrl.isNotBlank()) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.TopStart
                            ) {
                                HyperLinkButton(
                                    label = "Visit Homepage",
                                    link = state.repository.homepageUrl
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = state.repository.description,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Justify
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(
        snackBarHostState = remember { SnackbarHostState() },
        navController = rememberNavController(),
        state = DetailsState(
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
                language = "Kotlin",
                starsCount = 12
            )
        )
    )

}