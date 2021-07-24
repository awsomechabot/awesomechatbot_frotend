package com.example.awesomechatbot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class JoinActivity : AppCompatActivity() {
    private lateinit var retrofit: Retrofit
    private lateinit var retrofitInterface : RetrofitInterface
    private var BASE_URL = "http://172.30.1.25:3000"

    lateinit var edtId : EditText
    lateinit var edtPw : EditText
    lateinit var edtCheckPw : EditText
    lateinit var edtAge : EditText
    lateinit var edtNum : EditText
    lateinit var signBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        edtId = findViewById(R.id.edit_id)
        edtPw = findViewById(R.id.edit_pw)
        edtCheckPw = findViewById(R.id.edit_checkpw)
        edtAge = findViewById(R.id.edit_age)
        edtNum = findViewById(R.id.edit_number)
        signBtn = findViewById(R.id.signupBtn)

        // 회원가입 버튼 클릭 시
        signBtn.setOnClickListener {
            val map = HashMap<String, String>()
            map.put("email", edtId.text.toString())
            map.put("password", edtPw.text.toString())
            map.put("name", edtAge.text.toString())
            map.put("birth", edtNum.text.toString())

            val call = retrofitInterface.executeSignup(map)

            call.enqueue(object : Callback<Void?> {
                override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                    if (edtPw.text.toString() != edtCheckPw.text.toString()) {
                        Toast.makeText(this@JoinActivity,
                                "비밀번호를 확인해주십시오.", Toast.LENGTH_LONG).show()
                    }
                    else if (response.code() == 200) {
                        Toast.makeText(
                            this@JoinActivity,
                            "회원가입 성공. 환영합니다.", Toast.LENGTH_LONG
                        ).show()
                        var intent = Intent(
                            applicationContext,
                            LoginActivity::class.java
                        ) // 두번째 인자에 이동할 액티비티
                        startActivity(intent)
                    } else if (response.code() == 400) {
                        Toast.makeText(
                            this@JoinActivity, "이미 가입된 아이디입니다.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Void?>, t: Throwable) {
                    Toast.makeText(
                        this@JoinActivity, t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }

    }
}