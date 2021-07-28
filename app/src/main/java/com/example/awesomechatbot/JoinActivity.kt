package com.example.awesomechatbot

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class JoinActivity : AppCompatActivity() {
    private lateinit var retrofitBuilder: RetrofitBuilder
    private lateinit var retrofitInterface : RetrofitInteface
    private lateinit var edtId : EditText
    private lateinit var edtPw : EditText
    private lateinit var edtCheckPw : EditText
    private lateinit var edtName : EditText
    private lateinit var edtNum : EditText
    private lateinit var edtBirth : EditText
    private lateinit var signBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        retrofitBuilder = RetrofitBuilder
        retrofitInterface = retrofitBuilder.api

        edtId = findViewById(R.id.edit_id)
        edtPw = findViewById(R.id.edit_pw)
        edtCheckPw = findViewById(R.id.edit_checkpw)
        edtName = findViewById(R.id.edit_name)
        edtNum = findViewById(R.id.edit_number)
        signBtn = findViewById(R.id.signupBtn)
        edtBirth = findViewById(R.id.edit_birth)

        // 회원가입 버튼 클릭 시
        signBtn.setOnClickListener {
            val map = HashMap<String, String>()
            map.put("email", edtId.text.toString())
            map.put("password", edtPw.text.toString())
            map.put("name", edtName.text.toString())
            map.put("number", edtNum.text.toString())
            map.put("birth", edtBirth.text.toString())

            val call = retrofitInterface.executeSignup(map)

            call!!.enqueue(object : Callback<Void?> {
                override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                    if (response.code() == 200) {
                        Toast.makeText(this@JoinActivity,
                                "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show()
                        val intent = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(intent)
                    } else if (response.code() == 400) {
                        Toast.makeText(this@JoinActivity, "이미 가입된 정보입니다.",
                                Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<Void?>, t: Throwable) {
                    Toast.makeText(this@JoinActivity, t.message,
                            Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
