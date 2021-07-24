package com.example.awesomechatbot

data class RecordResult(val recordList: ArrayList<RecordItem>)
data class RecordModel(val recordItem: RecordItem)
data class Message(val message: String?= null)