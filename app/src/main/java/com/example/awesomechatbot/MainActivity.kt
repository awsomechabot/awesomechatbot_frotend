package com.example.awesomechatbot

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var retrofitBuilder: RetrofitBuilder
    private lateinit var retrofitInterface : RetrofitInteface

    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var intent = intent
      
        userId = intent.getStringExtra("user_id")
        var userNum = intent.getStringExtra("user_num")
        var userBirth = intent.getStringExtra("user_birth")
        var userName = intent.getStringExtra("user_name")

        retrofitBuilder = RetrofitBuilder
        retrofitInterface = retrofitBuilder.api

        var nav_header_view = navigationView.getHeaderView(0)
        nav_header_view.userName.text = userName + "님"
        nav_header_view.userInfo.text = userNum
        nav_header_view.userBirth.text = userBirth

        with(supportFragmentManager.beginTransaction()) {
            val fragment1 = Home_Fragment()
            val bundle = Bundle()
            bundle.putString("id", userId)
            fragment1.arguments = bundle
            replace(R.id.container, fragment1)
            commit()
        }

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.tab1 -> { // 하단 왼쪽 메뉴 탭 눌렀을 때
                    with(supportFragmentManager.beginTransaction()) {
                        val fragment1 = ChatbotFragment()
                        val bundle = Bundle()
                        bundle.putString("id", userId)
                        fragment1.arguments = bundle
                        replace(R.id.container, fragment1)
                        commit()
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab2 -> { // 하단 가운데 메뉴 탭 눌렀을 때
                    with(supportFragmentManager.beginTransaction()) {
                        val fragment2 = Home_Fragment()
                        val bundle = Bundle()
                        bundle.putString("id", userId)
                        fragment2.arguments = bundle
                        replace(R.id.container, fragment2)
                        commit()
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab3 -> { // 하단 오른쪽 메뉴 탭 눌렀을 때
                    with(supportFragmentManager.beginTransaction()) {
                        val fragment3 = ACC_Fragment()
                        val bundle = Bundle()
                        bundle.putString("id", userId)
                        fragment3.arguments = bundle
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

    fun setDataAtFragmentAcc(fragment: Fragment, array: ArrayList<String>, id: String) {
        val bundle = Bundle()
        bundle.putStringArrayList("array", array)
        bundle.putString("id", id)
        fragment.arguments = bundle
        setFragment(fragment)
    }

    
    fun setDataAtFragmentHome(fragment: Fragment, data: RecordItem) {
        val bundle = Bundle()
        bundle.putSerializable("item", data)
        fragment.arguments = bundle
        setFragment(fragment)
    }

    fun setDataId(fragment: Fragment, id: String) {
        val bundle = Bundle()
        bundle.putString("id", id)
        fragment.arguments = bundle
        setFragment(fragment)
    }

    fun setDataDday(fragment: Fragment, list: ArrayList<RecordItem>?, id: String) {
        val bundle = Bundle()
        bundle.putSerializable("list", list)
        bundle.putString("id", id)
        fragment.arguments = bundle
        setFragment(fragment)
    }

    // 데이터가 셋팅된 프래그먼트 띄우기
    fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    private var backPressedTime : Long = 0
    override fun onBackPressed() { // 이전키가 눌려졌을 때 자동 호출
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (System.currentTimeMillis() - backPressedTime < 2000) {
                ActivityCompat.finishAffinity(this)
                System.exit(0)
            }
            // 처음 클릭 메시지
            Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
            backPressedTime = System.currentTimeMillis()
        }

    }

}