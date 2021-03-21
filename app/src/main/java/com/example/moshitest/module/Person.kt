package com.example.moshitest.module

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Person(
    val name: String,
    val age: Int,
    val sex: Boolean
)