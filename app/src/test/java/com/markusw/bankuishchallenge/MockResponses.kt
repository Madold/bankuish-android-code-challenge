package com.markusw.bankuishchallenge

import com.markusw.bankuishchallenge.network.domain.model.GithubRepository

object MockResponses {

    val fakeRepositories = listOf(
        GithubRepository(
            id = 1,
            name = "kotlin-examples",
            fullName = "JetBrains/kotlin-examples",
            authorName = "JetBrains",
            authorAvatarUrl = "https://avatars.githubusercontent.com/u/878437?v=4",
            description = "Various examples for Kotlin",
            watchersCount = 1000,
            forksCount = 500,
            openIssuesCount = 10,
            topics = listOf("kotlin", "examples", "learning"),
            visibility = "public",
            defaultBranch = "master",
            homepageUrl = "https://kotlinlang.org",
            starsCount = 3000,
            language = "Kotlin"
        ),
        GithubRepository(
            id = 2,
            name = "kotlin-coroutines",
            fullName = "Kotlin/kotlin-coroutines",
            authorName = "Kotlin",
            authorAvatarUrl = "https://avatars.githubusercontent.com/u/1446536?v=4",
            description = "Library support for Kotlin coroutines",
            watchersCount = 2000,
            forksCount = 800,
            openIssuesCount = 50,
            topics = listOf("kotlin", "coroutines", "async"),
            visibility = "public",
            defaultBranch = "main",
            homepageUrl = "https://kotlinlang.org/docs/coroutines-overview.html",
            starsCount = 5000,
            language = "Kotlin"
        ),
        GithubRepository(
            id = 3,
            name = "ktor",
            fullName = "ktorio/ktor",
            authorName = "ktorio",
            authorAvatarUrl = "https://avatars.githubusercontent.com/u/28214161?v=4",
            description = "Framework for quickly creating connected applications in Kotlin with minimal effort",
            watchersCount = 1500,
            forksCount = 600,
            openIssuesCount = 30,
            topics = listOf("kotlin", "web-framework", "http-client", "http-server"),
            visibility = "public",
            defaultBranch = "main",
            homepageUrl = "https://ktor.io",
            starsCount = 4000,
            language = "Kotlin"
        )
    )

}