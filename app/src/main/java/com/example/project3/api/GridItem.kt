package com.example.project3.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.UUID

@JsonClass(generateAdapter = true)
data class GridItem (
    val title: String,
    val date: String,
    val url: String,
    @Json(name = "media_type") val mediaType: String,
    @Json(name = "explanation") val description: String
    )
@JsonClass(generateAdapter = true)
data class PhotoOfTheDay(
    val title: String,
    val date: String,
    val url: String,
    @Json(name = "media_type") val mediaType: String,
    @Json(name = "explanation") val description: String
)