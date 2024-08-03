package com.markusw.bankuishchallenge.main.domain

interface PaginationSource<T, R> {
    suspend fun loadNextPage(): List<R>
}