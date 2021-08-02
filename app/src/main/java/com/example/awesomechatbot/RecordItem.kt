package com.example.awesomechatbot

import java.io.Serializable

data class RecordItem (
        val user_id: String, // 사용자 아이디
        val hospital_name: String, // 병원명
        val disease_name: String, // 병명
        val today_date: String,  // 진료 날짜
        val part: String, // 부위
        val advice: String="", // 의사선생님의 말
        val redate: String="" // 재진 날짜
) : Serializable