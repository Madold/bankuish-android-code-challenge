package com.markusw.bankuishchallenge.di

import com.markusw.bankuishchallenge.details.presentation.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val detailsModule = module {
    viewModelOf(::DetailsViewModel)
}