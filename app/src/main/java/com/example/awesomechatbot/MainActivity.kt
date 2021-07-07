package com.example.awesomechatbot

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var nav_header_view = navigationView.getHeaderView(0)

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

    override fun onBackPressed() { // 이전키가 눌려졌을 때 자동 호출
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}