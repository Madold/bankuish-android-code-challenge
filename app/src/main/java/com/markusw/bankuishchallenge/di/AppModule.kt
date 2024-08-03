package com.markusw.bankuishchallenge.di

import com.markusw.bankuishchallenge.main.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)
}