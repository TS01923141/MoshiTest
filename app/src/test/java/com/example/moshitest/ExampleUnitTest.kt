package com.example.moshitest

import com.example.moshitest.module.NetworkManager
import com.example.moshitest.module.Person
import com.example.moshitest.module.currentweather.CurrentWeather
import com.example.moshitest.module.currentweather.WeatherService
import com.example.moshitest.module.newsdata.Item
import com.example.moshitest.module.newsdata.ItemDivider
import com.example.moshitest.module.newsdata.ItemNews
import com.example.moshitest.module.newsdata.NewsData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `json to moshi test`() {
//        val testJsonString = "{\"person\": { \"name\": \"Bob\", \"age\": 23, \"sex\": true }}"
        val testJsonString = "{\"name\": \"Bob\", \"age\": 23, \"sex\": true }"
        println(testJsonString)
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<Person> = moshi.adapter(Person::class.java)

        val person: Person? = jsonAdapter.fromJson(testJsonString)
        if (person == null) {
            println("person is null")
        } else {
            println("person $person")
        }
        assertEquals(person?.age, 23)
    }

    @Test
    fun `multi level json to moshi test`() {
        val testJsonString =
            """{"coord":{"lon":121.5319,"lat":25.0478},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"base":"stations","main":{"temp":297.79,"feels_like":300.67,"temp_min":296.48,"temp_max":299.15,"pressure":1011,"humidity":78},"visibility":9000,"wind":{"speed":1.54,"deg":310},"clouds":{"all":2},"dt":1616201517,"sys":{"type":1,"id":7949,"country":"TW","sunrise":1616191088,"sunset":1616234681},"timezone":28800,"id":1668341,"name":"Taipei","cod":200}"""
        println(testJsonString)
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<CurrentWeather> = moshi.adapter(CurrentWeather::class.java)

        val currentWeather = jsonAdapter.fromJson(testJsonString)
        if (currentWeather == null) {
            println("current weather is null")
        } else {
            print("current weather: $currentWeather")
        }
    }

//    @Test
//    fun `get json and convert to moshi`(){
//        val apiService = NetworkManager.provideRetrofit(NetworkManager.provideOkHttpClient()).create(WeatherService::class.java)
//
//        apiService.getWeather("taipei", "0899273c10ddbaae0059f020ae69e421", "metric").enqueue(object : retrofit2.Callback<CurrentWeather> {
//            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
//                println("failed")
//            }
//
//            override fun onResponse(
//                call: Call<CurrentWeather>,
//                response: Response<CurrentWeather>
//            ) {
//                println("succeed, current weather: " + response.body())
//            }
//
//        })
//
//        Thread.sleep(5000)
//    }

    @Test
    fun `get json and convert to moshi with coroutine`() {
        var requestJob: Job? = null

        val apiService = NetworkManager.provideRetrofit(NetworkManager.provideOkHttpClient())
            .create(WeatherService::class.java)

        requestJob = CoroutineScope(Dispatchers.IO).launch {
            println(Thread.currentThread().name)
            val response =
                apiService.getWeather("taipei", "0899273c10ddbaae0059f020ae69e421", "metric")
                    .await()
//            runBlocking(Dispatchers.Unconfined){
//                println(Thread.currentThread().name)
//                if (response.isSuccessful) {
//                    println("succeed, current weather: " + response.body())
//                } else {
//                    println("failed")
//                }
//            }
            withContext(Dispatchers.Unconfined) {
                println(Thread.currentThread().name)
                if (response.isSuccessful) {
                    println("succeed, current weather: " + response.body())
                } else {
                    println("failed")
                }
            }
        }

//        requestJob.cancel()

        Thread.sleep(5000)
    }

    //要在retrofit內使用時，moshi建立時新增ConverterFactory，之後加到retrofit內
    // Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
    //可參考https://stackoverflow.com/questions/68118813/retrofit-moshi-how-to-convert-an-array-of-objects-with-different-fields-to-pojo
    //library匯入-> implementation 'com.squareup.moshi:moshi-adapters:$version_code'
    @Test
    fun `difference type converter`() {
        val jsonString = "{ \"getVector\": { \"items\": [ { \"type\": \"divider\", \"title\": \"焦點新聞\", \"_meta\": { \"section\": \"焦點新聞\", \"category\": [ \"焦點新聞\" ] } },{ \"type\": \"news\", \"source\": \"fullscreenWeb\", \"ref\": \"https://news.tvbs.com.tw/world/1660910\", \"appearance\": { \"mainTitle\": \"快訊／香港銅鑼灣世貿施工現場傳火警　街道瀰漫濃煙\", \"subTitle\": \"TVBS新聞網\", \"thumbnail\": \"https://cc.tvbs.com.tw/img/upload/2021/12/15/20211215133719-7f325af8.jpg\", \"subscript\": \"news.tvbs.com.tw\" }, \"extra\": { \"created\": 1639549335 }, \"_meta\": { \"section\": \"焦點新聞\", \"category\": [ \"焦點新聞\" ] } }]}}"
        val moshi = Moshi.Builder()
                //加上多型適配器
            .add(PolymorphicJsonAdapterFactory.of(Item::class.java, "type")
                .withSubtype(ItemDivider::class.java, "divider")
                .withSubtype(ItemNews::class.java, "news"))
            .build()
        val itemData = moshi.adapter(NewsData::class.java).fromJson(jsonString)
        assertEquals(itemData?.getVector?.items?.size, 2)
    }
}