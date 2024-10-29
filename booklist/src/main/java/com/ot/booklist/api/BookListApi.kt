package com.ot.booklist.api

import retrofit2.http.GET
import retrofit2.http.Query

interface BookListApi {
    @GET("svc/books/v3/lists/current/hardcover-fiction.json")
    suspend fun getBooks(@Query("api-key") apiKey: String, @Query("offset") offset: Int): BookResponse
}
