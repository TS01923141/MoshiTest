package com.example.moshitest.module.currentweather

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

//    @GET("weather")
//    fun getWeather(
//        @Query("q") countryName: String,
//        @Query("appid") appId: String,
//        @Query("units") units: String
//    ): Call<CurrentWeather>

    @GET("weather")
    fun getWeather(
        @Query("q") countryName: String,
        @Query("appid") appId: String,
        @Query("units") units: String
    ): Deferred<Response<CurrentWeather>>
}