package com.markusw.bankuishchallenge.main.domain

import com.markusw.bankuishchallenge.core.utils.Result

interface PaginationSource<T, R> {
    suspend fun loadNextPage(): Result<List<R>>
}