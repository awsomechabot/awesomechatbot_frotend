package com.example.awesomechatbot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.fragment_add_.*
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Add_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Add_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var chkArray: ArrayList<String>

    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            chkArray = it.getStringArrayList("array") as ArrayList<String>
            userId = it.getString("id") as String
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 홈으로 돌아가기
        addHomeBtn.setOnClickListener {
            val mActivity = activity as MainActivity
            mActivity.setDataAtFragmentAcc(Home_Fragment(), chkArray, userId)
        }

        // 진료 기록 추가
        var cal = Calendar.getInstance()
        var cYear = cal.get(Calendar.YEAR)
        var cMonth = cal.get(Calendar.MONTH) // 1~12월 => 0~11
        var cDay = cal.get(Calendar.DAY_OF_MONTH)

        var rYear : Int = 0
        var rMonth : Int = 0
        var rDay : Int = 0

        datePicker.init(cYear, cMonth, cDay) { view, year, month, day ->
            rYear = year
            rMonth = month
            rDay = day
        }

        var item: RecordItem

        addRecordBtn.setOnClickListener {
            val tvHname = tvHname.text.toString() // 병원명
            val tvDname = tvDname.text.toString() // 병명
            val tvAdvice = tvAdvice.text.toString() // 의사선생님 말
            var tvRedate: String = ""
            if(rbRedate.isChecked) {
                tvRedate = rYear.toString() + "/" + (rMonth+1).toString() + "/" + rDay.toString()
            }
            val part = chkArray[0]
            val date = cYear.toString() + "/" + (cMonth+1).toString() + "/" + cDay.toString()


            item = RecordItem(userId, tvHname, tvDname, date, part, tvAdvice, tvRedate)
            RetrofitBuilder.api.addRecord(item)?.enqueue(object: retrofit2.Callback<Message?> {
                override fun onFailure(call: Call<Message?>, t: Throwable) {
                    Log.d("addError", t.message.toString())
                }

                override fun onResponse(call: Call<Message?>, response: Response<Message?>) {
                }

            })
            Toast.makeText(activity, "진료 기록을 추가하였습니다.", Toast.LENGTH_SHORT).show()
            val mActivity = activity as MainActivity
            mActivity.setDataAtFragmentAcc(Home_Fragment(), chkArray, userId)
        }

        //chkArray[0]에 해당하는 부위만 부위 이미지에 표시
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Add_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Add_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}