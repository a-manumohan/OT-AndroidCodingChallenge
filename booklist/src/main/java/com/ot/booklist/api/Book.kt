package com.ot.booklist.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Book(
    @Json(name = "primary_isbn10")
    val isbn: String,
    val title: String,
    @Json(name = "book_image")
    val image: String,
    val description: String,
)
