package com.markusw.bankuishchallenge.main.utils

import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.isLastItemVisible(): Boolean {
    return layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
}