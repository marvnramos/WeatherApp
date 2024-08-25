package com.example.weatherapp.data.repositories

import com.example.weatherapp.data.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIRepository {
    @GET("weather")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: Any,
        @Query("lang") language: String
    ): Call<WeatherResponse>
}