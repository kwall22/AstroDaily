package com.example.project3.api

import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "FDs73Vpktci6Z6lkVYXGM1hYgRynedBL24lPowBU"
interface NasaApi {
    @GET(
        "planetary/apod?api_key=$API_KEY" +
        "&count=50"
    )
    suspend fun fetchPhotos(): List<GridItem>

    @GET(
        "planetary/apod?api_key=$API_KEY"
    )
    suspend fun fetchPhotoOfTheDay(
        @Query("date") date: String,
        @Query("concept_tags") conceptTags: Boolean = true,
    ): PhotoOfTheDay
}