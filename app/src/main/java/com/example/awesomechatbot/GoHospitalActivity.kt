package com.example.awesomechatbot

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awesomechatbot.models.SearchKeywordResult
import kotlinx.android.synthetic.main.activity_go_hospital.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class GoHospitalActivity : AppCompatActivity() {
    val PERMISSIONS_REQUEST_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION)
    lateinit var map: MapView
    lateinit var userId: String

    private val listItems = arrayListOf<HospitalItem>()
    private val listAdapter = HospitalItemAdapter(listItems)

    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK 21ee0ad0730af7e0937a44681d3bfc46"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_hospital)

        listview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        listview.adapter = listAdapter
        listAdapter.setItemClickListener(object: HospitalItemAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val mapPoint = MapPoint.mapPointWithGeoCoord(listItems[position].y, listItems[position].x)
                map.setMapCenterPointAndZoomLevel(mapPoint, 0, true)
            }
        })

        var intent = intent
        var hospital = intent.getStringExtra("hospital")
        userId = intent.getStringExtra("id").toString()

        hospital = hospital?.trimEnd()
        var arr = hospital?.split(" ")

        map = MapView(this)
        mapView.addView(map)


        val permissionCheck = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            try {
                val userNowLocation: Location? = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                val uLatitude = userNowLocation?.latitude
                val uLongitude = userNowLocation?.longitude
                val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude!!, uLongitude!!)



                map.setMapCenterPoint(uNowPosition, true)

                val marker = MapPOIItem()
                marker.itemName = "현재 위치"
                marker.tag = 0
                marker.mapPoint = uNowPosition
                marker.markerType = MapPOIItem.MarkerType.RedPin // 기본으로 제공하는 BluePin 마커 모양.

                marker.selectedMarkerType =
                    MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.


                map.addPOIItem(marker)

                Log.d("arrsize", arr.toString())

                searchKeyword(arr!!, uLongitude, uLatitude)

            }catch (e: NullPointerException){
                Log.e("LOCATION_ERROR", e.toString())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.finishAffinity(this)

                }else{
                    ActivityCompat.finishAffinity(this)
                }

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("user_id", userId)
                startActivity(intent)
                finish()
            }
        }else{
            Toast.makeText(this, "위치 권한이 없습니다. 권한 획득 후 다시 실행해주세요~", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
        }


        goHomeBtn.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java) // 두번째 인자에 이동할 액티비티
            intent.putExtra("user_id", userId)
            //map.onSurfaceDestroyed()
            startActivity(intent)
            finish()
        }

    }


    fun searchKeyword(keywords: List<String>, uLongitude: Double, uLatitude: Double) {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create(RetrofitInteface::class.java)

        if (keywords.size == 0) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user_id", userId)
            startActivity(intent)
            finish()
        }

        for(i in keywords.indices) {
            val call = api.getSearchKeyword(API_KEY, keywords?.get(i).toString(), "HP8",  uLongitude.toString(), uLatitude.toString(), 5000)
            call.enqueue(object: Callback<SearchKeywordResult> {
                override fun onResponse(
                        call: Call<SearchKeywordResult>,
                        response: Response<SearchKeywordResult>
                ) {
                    // 통신 성공 (검색 결과는 response.body()에 담겨있음)
                    Log.d("Test", "Raw: ${response.raw()}")
                    Log.d("Test", "Body: ${response.body()}")
                    var rArr = response.body()?.documents
                    var markArray = arrayOfNulls<MapPOIItem>(rArr!!.size)
                    if(!rArr.isNullOrEmpty()) {
                        for(i in rArr.indices) {
                            val item = HospitalItem(rArr.get(i).place_name,
                                                    rArr.get(i).road_address_name,
                                                    rArr.get(i).phone,
                                                    rArr.get(i).x.toDouble(),
                                                    rArr.get(i).y.toDouble())
                            listItems.add(item)

                            markArray[i] = MapPOIItem()
                            markArray[i]?.itemName = rArr.get(i).place_name
                            markArray[i]?.tag = 0
                            markArray[i]?.mapPoint = MapPoint.mapPointWithGeoCoord(rArr.get(i).y.toDouble(), rArr.get(i).x.toDouble())
                            markArray[i]?.markerType = MapPOIItem.MarkerType.YellowPin

                            markArray[i]?.selectedMarkerType =
                                    MapPOIItem.MarkerType.BluePin


                            map.addPOIItem(markArray[i])
                        }
                        listAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<SearchKeywordResult>, t: Throwable) {
                    // 통신 실패
                    Log.w("callError", "통신 실패: ${t.message}")
                }
            })
        }
    }
}