package com.example.project3.api

import android.content.Context
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class AstroRepository(context: Context) {
    private val nasaApi: NasaApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        nasaApi = retrofit.create()
    }

    suspend fun fetchPhotos(): List<GridItem> =
        nasaApi.fetchPhotos()

    suspend fun fetchPhotoOfTheDay(): PhotoOfTheDay {
        val today = getCurrentDate()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        val nasaApi = retrofit.create(NasaApi::class.java)

        return nasaApi.fetchPhotoOfTheDay(today)
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = (calendar.get(Calendar.MONTH) + 1).toString().padStart(2, '0')
        val day = calendar.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0')
        Log.d("$year-$month-$day", "in get current date")
        return "$year-$month-$day"
    }

    companion object {
        private var INSTANCE: AstroRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = AstroRepository(context)
            }
        }

        fun get(): AstroRepository {
            return INSTANCE ?:
            throw IllegalStateException("AstroRepository must be initialized")
        }
    }
}