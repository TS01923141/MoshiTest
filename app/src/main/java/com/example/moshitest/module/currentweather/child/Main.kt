package com.example.moshitest.module.currentweather.child

import com.squareup.moshi.JsonClass

/*
    "main": {
        "temp": 24.64,
        "feels_like": 27.52,
        "temp_min": 23.33,
        "temp_max": 26,
        "pressure": 1011,
        "humidity": 78
    },
 */
@JsonClass(generateAdapter = true)
data class Main (
    val temp: Float,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float,
    val pressure: Int,
    val humidity: Int
)