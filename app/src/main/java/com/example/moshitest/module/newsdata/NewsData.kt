package com.example.moshitest.module.newsdata

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsData(
    val getVector: Vector
)

@JsonClass(generateAdapter = true)
data class Vector(
    val items: List<Item>
)

////用一個class接收所有type
//@JsonClass(generateAdapter = true)
//data class Item(
//    val type: String,
//    val title: String?,
//    val _meta: Meta?,
//    val source: String?,
//    val ref: String?,
//    val appearance: Appearance?,
//    val extra: Extra?
//)

//--
//用一個原型變化出不同type
interface Item{
    val type: String
}

@JsonClass(generateAdapter = true)
data class ItemDivider(
    override val type: String,
    val title: String?,
    val _meta: Meta?
): Item

@JsonClass(generateAdapter = true)
data class ItemNews(
    override val type: String,
    val source: String?,
    val ref: String?,
    val appearance: Appearance?,
    val extra: Extra?,
    val _meta: Meta?
): Item
//--

@JsonClass(generateAdapter = true)
data class Appearance(
    val mainTitle: String?,
    val subTitle: String?,
    val thumbnail: String?,
    val subscript: String?
)

@JsonClass(generateAdapter = true)
data class Extra(
    val created: Long?
)

@JsonClass(generateAdapter = true)
data class Meta(
    val section: String?,
    val category: List<String>?
)