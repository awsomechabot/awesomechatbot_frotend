package com.example.awesomechatbot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.awesomechatbot.adapters.ChatAdapter
import com.example.awesomechatbot.helpers.SendMessageInBg
import com.example.awesomechatbot.interfaces.BotReply
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.dialogflow.v2.*
import com.google.common.collect.Lists
import com.example.awesomechatbot.models.Message
import kotlinx.android.synthetic.main.fragment_chatbot.view.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatbotFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class ChatbotFragment : Fragment(), BotReply {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var chatView: RecyclerView
    lateinit var chatAdapter: ChatAdapter
    var messageList = ArrayList<Message>()
    lateinit var editMessage: EditText
    lateinit var btnSend: ImageButton
    var check : Int = 0
    var point : String = ""
    var hospital : String = ""
    //dialogFlow
    private var sessionsClient: SessionsClient? = null
    private var sessionName: SessionName? = null
    private val uuid = UUID.randomUUID().toString()
    private val TAG = "mainactivity"

    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            userId = it.getString("id") as String
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_chatbot, container, false)
        chatView = view.findViewById<RecyclerView>(R.id.chatView)
        editMessage = view.findViewById<EditText>(R.id.editMessage)
        btnSend = view.findViewById<ImageButton>(R.id.btnSend)

        chatAdapter = ChatAdapter(messageList, this)
        chatView.adapter = chatAdapter

        view.btnSend!!.setOnClickListener {
            val message = editMessage!!.text.toString()
            if (!message.isEmpty()) {
                messageList.add(Message(message, false))
                editMessage!!.setText("")
                sendMessageToBot(message)
                Objects.requireNonNull(chatView!!.adapter)!!.notifyDataSetChanged()
                Objects.requireNonNull(chatView!!.layoutManager)
                        ?.scrollToPosition(messageList.size - 1)
                if(check==1){
                    if(message.contains("배")){
                        point += "배 "
                        hospital += "내과 "
                    }
                    if(message.contains("머리")){
                        point += "머리 "
                        hospital += "신경과 "
                    }
                    if(message.contains("다리")) {
                        point += "다리 "
                        hospital +="정형외과 "
                    }
                    check=0
                }
            } else {
                Toast.makeText(getActivity(), "Please enter text!", Toast.LENGTH_SHORT).show()
            }
        }

        setUpBot()

        return view
    }

    private fun setUpBot() {
        try {
            val stream = this.resources.openRawResource(R.raw.credential)
            val credentials = GoogleCredentials.fromStream(stream)
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"))
            val projectId = (credentials as ServiceAccountCredentials).projectId
            val settingsBuilder = SessionsSettings.newBuilder()
            val sessionsSettings = settingsBuilder.setCredentialsProvider(
                    FixedCredentialsProvider.create(credentials)).build()
            sessionsClient = SessionsClient.create(sessionsSettings)
            sessionName = SessionName.of(projectId, uuid)

            Log.d(TAG, "projectId : $projectId")
        } catch (e: Exception) {
            Log.d(TAG, "setUpBot: " + e.message)
        }
    }

    private fun sendMessageToBot(message: String) {
        val input = QueryInput.newBuilder()
                .setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build()
        SendMessageInBg(this, sessionName!!, sessionsClient!!, input).execute()
    }

    override fun callback(returnResponse: DetectIntentResponse?) {
        if (returnResponse != null) {
            val botReply = returnResponse.queryResult.fulfillmentText
            if (!botReply.isEmpty()) {
                messageList.add(Message(botReply, true))
                chatAdapter!!.notifyDataSetChanged()
                Objects.requireNonNull(chatView!!.layoutManager)?.scrollToPosition(messageList.size - 1)

                // 추가
                if(botReply.contains("있군요.") or botReply.contains("있으시군요.")){
                    var intent = Intent(activity, NoticeActivity::class.java) // 두번째 인자에 이동할 액티비티
                    intent.putExtra("pain", point)
                    intent.putExtra("hospital", hospital)
                    intent.putExtra("id", userId)
                    startActivityForResult(intent,0)
                }
                else if(botReply.contains("건강하군요.") or botReply.contains("다행이에요.") or botReply.contains("아픈 곳이 없군요.")){
                    var intent = Intent(activity, CheckEndActivity::class.java) // 두번째 인자에 이동할 액티비티
                    intent.putExtra("id", userId)
                    startActivity(intent)
                }
                else if(botReply.contains("어디가") or botReply.contains("아픈가요?")){
                    check=1
                }
            } else {
                Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(getActivity(), "failed to connect!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatbotFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ChatbotFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}