package com.example.awesomechatbot

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var itemList : ArrayList<RecordItem>? = arrayListOf()
    private var itemToList : List<RecordItem> = listOf()
    private var dateList : List<String?> = listOf()
    private var ddayList : ArrayList<Int> = arrayListOf()

    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            userId = it.getString("id") as String
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.best_recycler)
        val manager  = LinearLayoutManager(activity)
        Log.d("userId", userId)
        RetrofitBuilder.api.getRecords(userId)?.enqueue(object: retrofit2.Callback<ArrayList<RecordItem>> {
            override fun onFailure(call: Call<ArrayList<RecordItem>>, t: Throwable) {
                Log.d("tmessage", t.message.toString())
            }

            override fun onResponse(call: Call<ArrayList<RecordItem>>, response: Response<ArrayList<RecordItem>>) {
                var result = response.body()
                itemList = result
                itemToList = itemList?.toList()!!
                if(itemToList != null) {
                    itemToList = itemToList.filter { it.redate != "" }
                    dateList = itemToList.map(RecordItem::redate)
                }

                Log.d("itemList", itemList.toString())
                if(itemList != null) {
                    recyclerView.adapter = RecordItemAdapter(itemList!!)
                    val minDay = calcDay(dateList)
                    Log.d("minDay", minDay.toString())
                    if (minDay != null) {
                        if (minDay > 0) {
                            home_dday.text = "D-${minDay}"
                        } else if (minDay == 0){
                            home_dday.text = "D-day"
                        } else {
                            home_dday.text = "디데이 없음"
                        }
                    } else {
                        home_dday.text = "디데이 없음"
                    }
                }
            }

        })
        // 진료기록 역순으로 출력
        manager.reverseLayout = true
        manager.stackFromEnd = true
        recyclerView.layoutManager = manager

        home_dday.setOnClickListener {
            val mActivity = activity as MainActivity
            mActivity.setDataDday(Dday_Fragment(), itemList, userId)
        }
    }

    fun calcDay(dList: List<String?>): Int? {
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time.time
        Log.d("today", (today/ (24 * 60 * 60 * 1000)).toString())
        for(i in dList.indices) {
            val str = dList[i]?.replace("/", "")
            val strBuilder = StringBuilder(str)
            strBuilder.insert(4, "0")
            Log.d("str", strBuilder.toString())
            val endDate = dateFormat.parse(strBuilder.toString()).time
            Log.d("endDate", endDate.toString())
            val dday = ((endDate - today)/ (24 * 60 * 60 * 1000)).toInt()
            Log.d("dday", ((endDate - today)/ (24 * 60 * 60 * 1000)).toString())
            if(dday >= 0) {
                ddayList.add(((endDate - today)/ (24 * 60 * 60 * 1000)).toInt())
            }
        }
        return ddayList.min()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}