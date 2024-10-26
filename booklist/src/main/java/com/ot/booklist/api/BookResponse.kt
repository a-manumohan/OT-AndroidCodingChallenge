package com.ot.booklist.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookResponse(
    val results: Results
) {
    @JsonClass(generateAdapter = true)
    data class Results(
        val books: List<Book>
    )
}
