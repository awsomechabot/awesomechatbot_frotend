package com.example.awesomechatbot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class LoginActivity : AppCompatActivity() {
    private lateinit var retrofit: Retrofit
    private lateinit var retrofitInterface : RetrofitInteface
    private var BASE_URL = "http://172.30.1.25:3000" // ip 주소 변경

    private lateinit var loginBtn : Button
    private lateinit var signupBtn : Button
    private lateinit var idLogin : EditText
    private lateinit var pwLogin : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitInterface = retrofit.create(RetrofitInteface::class.java)

        loginBtn = findViewById(R.id.btn_login)
        signupBtn = findViewById(R.id.btn_register)

        // 로그인 버튼 클릭 시
        loginBtn.setOnClickListener {
            idLogin = findViewById(R.id.ID_login)
            pwLogin = findViewById(R.id.PW_login)

            val map = HashMap<String, String>()
            map.put("email", idLogin.text.toString())
            map.put("password", pwLogin.text.toString())

            val call = retrofitInterface.executeLogin(map)

            if (call != null) {
                call.enqueue(object : Callback<LoginResult?> {
                    override fun onResponse(
                            call: Call<LoginResult?>,
                            response: Response<LoginResult?>
                    ) {
                        if (response.code() == 200) {
                            val result = response.body()
                            val builder1 = AlertDialog.Builder(this@LoginActivity)
                            var intent = Intent(applicationContext, MainActivity::class.java) // 두번째 인자에 이동할 액티비티
                            intent.putExtra("user_id",result!!.id)
                            intent.putExtra("user_pw",result.password)

                            builder1.setTitle(result.id)
                            builder1.setMessage(result.password)
                            builder1.show()

                            startActivityForResult(intent,0)

                        } else if (response.code() == 404) {
                            Toast.makeText(
                                    this@LoginActivity, "404 에러 페이지",
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResult?>, t: Throwable) {
                        Toast.makeText(
                                this@LoginActivity, t.message,
                                Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }
        // 회원가입 버튼 클릭 시
        signupBtn.setOnClickListener {
            var intent = Intent(applicationContext, JoinActivity::class.java) // 두번째 인자에 이동할 액티비티
            startActivity(intent)
        }
    }
}