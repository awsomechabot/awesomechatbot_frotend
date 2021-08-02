package com.example.awesomechatbot

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_dday.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Dday_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Dday_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var itemList: ArrayList<RecordItem>? = arrayListOf()

    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            itemList = it.getSerializable("list") as ArrayList<RecordItem>?
            userId = it.getString("id") as String
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dday, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.dday_recycler)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time.time

        var minDay : Int = 0
        var index = 0
        var count = 0
        var count2 = 0
        var ddayList: ArrayList<DdayItem> = arrayListOf()
        Log.d("ddayItemList", itemList.toString())
        if (itemList != null) {
            for(i in itemList!!.indices) {
                val str = itemList!![i].redate.replace("/", "")
                if(str != "") {
                    val strBuilder = StringBuilder(str)
                    strBuilder.insert(4, "0")
                    Log.d("str", strBuilder.toString())
                    val endDate = dateFormat.parse(strBuilder.toString()).time
                    val dday = ((endDate - today)/ (24 * 60 * 60 * 1000)).toInt()
                    if(dday > 0) {
                        count += 1
                        if(i == 0 || count2 == 0) {
                            minDay = dday
                            count2 += 1
                            index = i
                        } else {
                            if(minDay > dday) {
                                minDay = dday
                                index = i
                            }
                        }
                        ddayList.add(DdayItem(itemList!![i].user_id, itemList!![i].hospital_name, itemList!![i].disease_name, itemList!![i].today_date, itemList!![i].part,
                            itemList!![i].redate, dday))
                    }
                }
            }
            Log.d("index", index.toString())
            if(count == 0) {
                centerWhat.text = ""
                centerDday.text = "디데이 X"
            } else {
                ddayList.sortBy(DdayItem::dday)
                centerWhat.text = itemList!![index].hospital_name + " " + itemList!![index].disease_name + " 재진"
                centerDday.text = "D-" + minDay
                recyclerView.adapter = DdayItemAdapter(ddayList)
            }
        } else {
            centerWhat.text = ""
            centerDday.text = "디데이 X"
        }


        returnHomeBtn.setOnClickListener {
            val mActivity = activity as MainActivity
            mActivity.setDataId(Home_Fragment(), userId)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Dday_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                Dday_Fragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}