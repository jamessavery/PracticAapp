package com.example.data.services.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuoteList(
    val count: Int? = null,
    val lastItemIndex: Int? = null,
    val page: Int? = null,
    val results: List<Result>? = null,
    val totalCount: Int? = null,
    val totalPages: Int? = null
)