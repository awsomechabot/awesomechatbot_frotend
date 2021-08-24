package com.example.awesomechatbot

import android.content.Intent
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
        val userId = intent.getStringExtra("id")

        textResult.setText("아픈 부위 : " + pain + "\n"+"가야할 병원 : " + hospital)

        goHospitalBtn.setOnClickListener {
            var intent2 = Intent(applicationContext, GoHospitalActivity::class.java) // 두번째 인자에 이동할 액티비티
            intent2.putExtra("hospital", hospital)
            intent2.putExtra("id", userId)
            startActivity(intent2)
            finish()
        }
    }
}