package com.markusw.bankuishchallenge.network.domain.model

import com.markusw.bankuishchallenge.network.data.remote.responses.RepositoryResponse

data class GithubRepository(
    val id: Int = 0,
    val name: String = "",
    val fullName: String = "",
    val authorName: String = "",
    val authorAvatarUrl: String = "",
    val description: String = "",
    val watchersCount: Int = 0,
    val forksCount: Int = 0,
    val openIssuesCount: Int = 0,
    val topics: List<String> = emptyList(),
    val visibility: String = "",
    val defaultBranch: String = "",
    val homepageUrl: String = "",
    val starsCount: Int = 0,
    val language: String = ""
)

fun RepositoryResponse.toDomainModel() = GithubRepository(
    id = this.id,
    name = this.name,
    fullName = this.full_name,
    authorName = this.owner.login,
    description = this.description ?: "",
    watchersCount = this.watchers_count,
    forksCount = this.forks_count,
    openIssuesCount = this.open_issues_count,
    topics = this.topics,
    visibility = this.visibility,
    defaultBranch = this.default_branch,
    authorAvatarUrl = this.owner.avatar_url,
    homepageUrl = this.homepage ?: "",
    starsCount = this.stargazers_count,
    language = this.language ?: ""
)