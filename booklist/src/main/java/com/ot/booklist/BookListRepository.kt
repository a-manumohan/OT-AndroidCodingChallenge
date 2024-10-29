package com.ot.booklist

import com.ot.booklist.api.Book
import com.ot.booklist.api.BookListApi
import javax.inject.Inject

interface BookListRepository {
    suspend fun fetchBooks(offset: Int): Result<List<Book>>
}

class BookListRepositoryImpl
@Inject
constructor(private val bookListApi: BookListApi) : BookListRepository {
    private val apiKey =
        "KoRB4K5LRHygfjCL2AH6iQ7NeUqDAGAB" // this should ideally come from an ENV variable in the ci/cd setup

    override suspend fun fetchBooks(offset: Int): Result<List<Book>> = runCatching {
        bookListApi.getBooks(
            apiKey = apiKey,
            offset = offset,
        )
    }.mapCatching { it.results.books }
}
