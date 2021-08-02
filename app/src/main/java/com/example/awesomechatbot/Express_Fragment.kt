package com.example.awesomechatbot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_express_.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Express_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Express_Fragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_express_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvHead1.visibility = View.VISIBLE
        tvHead2.visibility = View.VISIBLE
        tvBelly1.visibility = View.VISIBLE
        tvBelly2.visibility = View.VISIBLE
        tvHB.visibility = View.VISIBLE
        tvNail1.visibility = View.VISIBLE
        tvNail2.visibility = View.VISIBLE
        tvTooth1.visibility = View.VISIBLE
        tvTooth2.visibility = View.VISIBLE
        tvNT.visibility = View.VISIBLE

        if(chkArray.contains("머리")) {
            if(chkArray.contains("배")) {
                if(chkArray.contains("발톱/손톱")) {
                    if(chkArray.contains("치아")) {
                        tvHB.visibility = View.GONE
                        tvTooth2.visibility = View.GONE
                    } else {
                        tvHB.visibility = View.GONE
                        tvNail2.visibility = View.GONE
                        tvTooth1.visibility = View.GONE
                        tvNT.visibility = View.GONE
                    }
                } else {
                    if(chkArray.contains("치아")) {
                        tvHB.visibility = View.GONE
                        tvTooth2.visibility = View.GONE
                        tvNail1.visibility = View.GONE
                        tvNail2.visibility = View.GONE
                    } else {
                        tvBelly2.visibility = View.GONE
                        tvNail1.visibility = View.GONE
                        tvNail2.visibility = View.GONE
                        tvTooth1.visibility = View.GONE
                        tvTooth2.visibility = View.GONE
                        tvNT.visibility = View.GONE
                    }
                }
            } else {
                if(chkArray.contains("발톱/손톱")) {
                    if(chkArray.contains("치아")) {
                        tvBelly1.visibility = View.GONE
                        tvBelly2.visibility = View.GONE
                        tvHB.visibility = View.GONE
                        tvTooth2.visibility = View.GONE
                    } else {
                        tvBelly1.visibility = View.GONE
                        tvBelly2.visibility = View.GONE
                        tvHB.visibility = View.GONE
                        tvNail2.visibility = View.GONE
                        tvTooth1.visibility = View.GONE
                        tvNT.visibility = View.GONE
                    }
                } else {
                    if(chkArray.contains("치아")) {
                        tvBelly1.visibility = View.GONE
                        tvBelly2.visibility = View.GONE
                        tvHB.visibility = View.GONE
                        tvTooth2.visibility = View.GONE
                        tvNail1.visibility = View.GONE
                        tvNail2.visibility = View.GONE
                    } else {
                        tvHead2.visibility = View.GONE
                        tvBelly1.visibility = View.GONE
                        tvBelly2.visibility = View.GONE
                        tvNail1.visibility = View.GONE
                        tvNail2.visibility = View.GONE
                        tvTooth1.visibility = View.GONE
                        tvTooth2.visibility = View.GONE
                        tvNT.visibility = View.GONE
                    }
                }
            }
        } else {
            if(chkArray.contains("배")) {
                if(chkArray.contains("발톱/손톱")) {
                    if(chkArray.contains("치아")) {
                        tvHead1.visibility = View.GONE
                        tvHead2.visibility = View.GONE
                        tvHB.visibility = View.GONE
                        tvTooth2.visibility = View.GONE
                    } else {
                        tvHead1.visibility = View.GONE
                        tvHead2.visibility = View.GONE
                        tvHB.visibility = View.GONE
                        tvNail2.visibility = View.GONE
                        tvTooth1.visibility = View.GONE
                        tvNT.visibility = View.GONE
                    }
                } else {
                    if(chkArray.contains("치아")) {
                        tvHead1.visibility = View.GONE
                        tvHead2.visibility = View.GONE
                        tvHB.visibility = View.GONE
                        tvTooth2.visibility = View.GONE
                        tvNail1.visibility = View.GONE
                        tvNail2.visibility = View.GONE
                    } else {
                        tvHead1.visibility = View.GONE
                        tvHead2.visibility = View.GONE
                        tvBelly2.visibility = View.GONE
                        tvNail1.visibility = View.GONE
                        tvNail2.visibility = View.GONE
                        tvTooth1.visibility = View.GONE
                        tvTooth2.visibility = View.GONE
                        tvNT.visibility = View.GONE
                    }
                }
            } else {
                if(chkArray.contains("발톱/손톱")) {
                    if(chkArray.contains("치아")) {
                        tvHead1.visibility = View.GONE
                        tvHead2.visibility = View.GONE
                        tvBelly1.visibility = View.GONE
                        tvBelly2.visibility = View.GONE
                        tvHB.visibility = View.GONE
                        tvTooth2.visibility = View.GONE
                    } else {
                        tvHead1.visibility = View.GONE
                        tvHead2.visibility = View.GONE
                        tvBelly1.visibility = View.GONE
                        tvBelly2.visibility = View.GONE
                        tvHB.visibility = View.GONE
                        tvNail2.visibility = View.GONE
                        tvTooth1.visibility = View.GONE
                        tvNT.visibility = View.GONE
                    }
                } else {
                    if(chkArray.contains("치아")) {
                        tvHead1.visibility = View.GONE
                        tvHead2.visibility = View.GONE
                        tvBelly1.visibility = View.GONE
                        tvBelly2.visibility = View.GONE
                        tvHB.visibility = View.GONE
                        tvTooth2.visibility = View.GONE
                        tvNail1.visibility = View.GONE
                        tvNail2.visibility = View.GONE
                    } else {
                        tvHead1.visibility = View.GONE
                        tvHead2.visibility = View.GONE
                        tvHead2.visibility = View.GONE
                        tvBelly1.visibility = View.GONE
                        tvBelly2.visibility = View.GONE
                        tvNail1.visibility = View.GONE
                        tvNail2.visibility = View.GONE
                        tvTooth1.visibility = View.GONE
                        tvTooth2.visibility = View.GONE
                        tvNT.visibility = View.GONE
                    }
                }
            }
        }

        goAddBtn.setOnClickListener {
            val mActivity = activity as MainActivity
            mActivity.setDataAtFragmentAcc(Add_Fragment(), chkArray, userId)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Express_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Express_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}