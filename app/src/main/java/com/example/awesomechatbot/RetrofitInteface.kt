package com.example.awesomechatbot

import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface RetrofitInteface {
    @POST("/login")
    fun executeLogin(@Body map: HashMap<String, String>): Call<LoginResult?>?

    @POST("/signup")
    fun executeSignup(@Body map: HashMap<String, String>): Call<Void?>?

    @GET("/records")
    fun getRecords(@Query("uid") uid: String): Call<ArrayList<RecordItem>>?

    @POST("/records")
    fun addRecord(@Body item: RecordItem): Call<Message?>?
}