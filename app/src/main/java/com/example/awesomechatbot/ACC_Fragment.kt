 package com.example.awesomechatbot

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_acc.*

 // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ACC_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ACC_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mContext : Context

    private var userId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            userId = it.getString("id") as String?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_acc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var chkArray : ArrayList<String> = arrayListOf()

        chkHead.setOnCheckedChangeListener { compoundButton, b ->
            if (chkHead.isChecked == true) {
                chkArray.add("머리");
            } else {
                for(i in chkArray.indices) {
                    if(chkArray[i] == "머리") {
                        chkArray.removeAt(i)
                    }
                }
            }
        }

        chkBelly.setOnCheckedChangeListener { compoundButton, b ->
            if (chkBelly.isChecked == true) {
                chkArray.add("배");
            } else {
                for(i in chkArray.indices) {
                    if(chkArray[i] == "배") {
                        chkArray.removeAt(i)
                    }
                }
            }
        }

        chkNail.setOnCheckedChangeListener { compoundButton, b ->
            if (chkNail.isChecked == true) {
                chkArray.add("발톱/손톱");
            } else {
                for(i in chkArray.indices) {
                    if(chkArray[i] == "발톱/손톱") {
                        chkArray.removeAt(i)
                    }
                }
            }
        }

        chkTooth.setOnCheckedChangeListener { compoundButton, b ->
            if (chkTooth.isChecked == true) {
                chkArray.add("치아");
            } else {
                for(i in chkArray.indices) {
                    if(chkArray[i] == "치아") {
                        chkArray.removeAt(i)
                    }
                }
            }
        }

        goExpressBtn.setOnClickListener {
            if(chkArray.isEmpty()) {
                Toast.makeText(mContext, "아픈 곳을 체크해주세요", Toast.LENGTH_SHORT).show()
            } else {
                val mActivity = activity as MainActivity
                mActivity.setDataAtFragmentAcc(Express_Fragment(), chkArray, userId!!)
            }
        }
    }

    override fun onAttach(activity: Activity) { // 프래그먼트가 메인액티비티에 붙을때실행
        super.onAttach(activity)
        if (context is MainActivity) {
            mContext = context as MainActivity
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ACC_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ACC_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}