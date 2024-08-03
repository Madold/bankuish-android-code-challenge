package com.markusw.bankuishchallenge.di

import com.markusw.bankuishchallenge.network.data.remote.services.AndroidGithubService
import com.markusw.bankuishchallenge.network.data.repository.AndroidGithubReposRepository
import com.markusw.bankuishchallenge.network.domain.remote.services.GithubService
import com.markusw.bankuishchallenge.network.domain.repository.GithubReposRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        prettyPrint = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

    single<GithubService> {
        AndroidGithubService(get())
    }

    single<GithubReposRepository> {
        AndroidGithubReposRepository(get())
    }

}