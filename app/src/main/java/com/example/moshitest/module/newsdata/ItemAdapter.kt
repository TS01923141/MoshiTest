package com.example.moshitest.module.newsdata

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import org.json.JSONObject

//class ItemAdapter {
////    @ToJson
////    fun toJson(item: Item): Item {
////
////    }
//
//    @FromJson
//    fun fromJson(jsonString: String): Item {
//        val moshi = Moshi.Builder().build()
//        val jsonObject = JSONObject(jsonString)
//        val type = jsonObject.getString("type")
//        val adapter = when(type){
//            "divider" -> {
//                moshi.adapter(ItemDivider::class.java)
//            }
//            "news" -> {
//                moshi.adapter(ItemNews::class.java)
//            }
//            else -> throw IllegalArgumentException("unhandled type")
//        }
//        return adapter.fromJson(jsonString)!!
//    }
//}