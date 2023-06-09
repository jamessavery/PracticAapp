package com.example.data.services.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    val _id: String = "",
    val author: String = "",
    val authorSlug: String = "",
    val content: String = "",
    val dateAdded: String = "",
    val dateModified: String = "",
    val length: Int = 0,
    val tags: List<String> = listOf()
)