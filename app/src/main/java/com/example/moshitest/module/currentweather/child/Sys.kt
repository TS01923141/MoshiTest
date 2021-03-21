package com.example.moshitest.module.currentweather.child

import com.squareup.moshi.JsonClass

/*
    "sys": {
        "type": 1,
        "id": 7949,
        "country": "TW",
        "sunrise": 1616191088,
        "sunset": 1616234681
    },
 */
@JsonClass(generateAdapter = true)
data class Sys (
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Double,
    val sunset: Double
)