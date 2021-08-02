package com.example.awesomechatbot

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    var api: RetrofitInteface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.30.1.52:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(RetrofitInteface::class.java)
    }
}