package com.example.awesomechatbot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var retrofit: Retrofit
    private lateinit var retrofitInterface : RetrofitInteface
    private var BASE_URL = "http://172.30.1.25:3000" // ip 주소 변경

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var intent = intent
        var userId = intent.getStringArrayExtra("user_id")
        var userPw = intent.getStringArrayExtra("user_pw")
        Toast.makeText(applicationContext, userId.toString(), Toast.LENGTH_LONG).show()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitInterface = retrofit.create(RetrofitInteface::class.java)

        var nav_header_view = navigationView.getHeaderView(0)
        nav_header_view.userName.text = userId.toString()

        with(supportFragmentManager.beginTransaction()) {
            val fragment1 = Home_Fragment()
            replace(R.id.container, fragment1)
            commit()
        }
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.tab1 -> { // 하단 왼쪽 메뉴 탭 눌렀을 때
                    with(supportFragmentManager.beginTransaction()) {
                        val fragment1 = ChatbotFragment()
                        replace(R.id.container, fragment1)
                        commit()
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab2 -> { // 하단 가운데 메뉴 탭 눌렀을 때
                    with(supportFragmentManager.beginTransaction()) {
                        val fragment2 = Home_Fragment()
                        replace(R.id.container, fragment2)
                        commit()
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab3 -> { // 하단 오른쪽 메뉴 탭 눌렀을 때
                    with(supportFragmentManager.beginTransaction()) {
                        val fragment3 = ACC_Fragment()
                        replace(R.id.container, fragment3)
                        commit()
                    }
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }

        navigationView.setNavigationItemSelectedListener { //네비게이션뷰 이벤트리스너
            when(it.itemId) {
                R.id.item1 -> { // 로그아웃
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }
    }

    fun setDataAtFragmentAcc(fragment: Fragment, array: ArrayList<String>) {
        val bundle = Bundle()
        bundle.putStringArrayList("array", array)
        fragment.arguments = bundle
        setFragment(fragment)
    }

    fun setDataAtFragmentHome(fragment: Fragment, data: RecordItem) {
        val bundle = Bundle()
        bundle.putSerializable("item", data)
        fragment.arguments = bundle
        setFragment(fragment)
    }

    // 데이터가 셋팅된 프래그먼트 띄우기
    fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun onBackPressed() { // 이전키가 눌려졌을 때 자동 호출
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}