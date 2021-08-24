package com.example.awesomechatbot

import com.example.awesomechatbot.models.SearchKeywordResult
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

    @GET("v2/local/search/keyword.json")
    fun getSearchKeyword(
        @Header("Authorization") key: String, // 카카오 API 인증키
        @Query("query") query: String, // 검색 질의어
        @Query("category_group_code") category: String, // 카테고리 그룹 코드
        @Query("x") x: String, // 중심 좌표의 x값 혹은 longitude
        @Query("y") y: String, // 중심 좌표의 y값 혹은 latitude
        @Query("radius") radius: Int // 중심 좌표부터의 반경거리
    ): Call<SearchKeywordResult>
}