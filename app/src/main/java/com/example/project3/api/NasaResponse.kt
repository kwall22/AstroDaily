package com.example.project3.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NasaResponse ( //this is my top level List of grid items
    //mix of Flicker response bc top level
    //and photo response bc list
    val gridItems: List<GridItem>
)