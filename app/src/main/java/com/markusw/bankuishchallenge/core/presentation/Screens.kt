package com.markusw.bankuishchallenge.core.presentation

sealed class Screens(val route: String) {

    data object Main: Screens(route = "main")
    data object Details: Screens(route = "details")

}