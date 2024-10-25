package com.ot.booklist.api

import com.squareup.moshi.Json

data class Book(
    @Json(name = "primary_isbn10")
    val isbn: String,
    val title: String,
    @Json(name = "book_image")
    val image: String,
    val description: String,
)
