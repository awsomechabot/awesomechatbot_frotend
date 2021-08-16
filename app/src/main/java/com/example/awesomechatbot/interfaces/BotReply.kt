package com.example.awesomechatbot.interfaces

import com.google.cloud.dialogflow.v2.DetectIntentResponse

interface BotReply {
    fun callback(returnResponse: DetectIntentResponse?)
}