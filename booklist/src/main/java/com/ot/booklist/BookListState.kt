package com.ot.booklist

import com.ot.booklist.ui.UiBook
import com.ot.core.StringResource

sealed interface BookListState {
    data object Loading : BookListState

    data class Books(val items: List<UiBook>) : BookListState

    data class Error(val message: StringResource) : BookListState
}
