package com.example.moshitest.module.currentweather.child

import com.squareup.moshi.JsonClass

/*
    "clouds": {
        "all": 2
    },
 */
@JsonClass(generateAdapter = true)
data class Clouds (
    val all: Int
)