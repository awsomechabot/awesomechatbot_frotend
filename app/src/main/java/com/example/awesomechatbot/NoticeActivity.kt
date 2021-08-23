package com.example.awesomechatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_notice.*

class NoticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        var intent = intent
        val pain = intent.getStringExtra("pain")
        val hospital = intent.getStringExtra("hospital")

        textResult.setText("아픈 부위 : " + pain + "\n"+"가야할 병원 : " + hospital)
    }
}