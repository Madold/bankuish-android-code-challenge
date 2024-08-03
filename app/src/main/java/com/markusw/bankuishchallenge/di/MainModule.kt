package com.markusw.bankuishchallenge.di

import com.markusw.bankuishchallenge.main.data.GithubPaginationSource
import org.koin.dsl.module

val mainModule = module {
    single {
        GithubPaginationSource(get())
    }
}