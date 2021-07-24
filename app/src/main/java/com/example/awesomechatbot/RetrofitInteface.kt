package com.example.awesomechatbot

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

interface RetrofitInteface {
    @POST("/login")
    fun executeLogin(@Body map: HashMap<String, String>): Call<LoginResult?>?

    @POST("/signup")
    fun executeSignup(@Body map: HashMap<String, String>): Call<Void?>?
}