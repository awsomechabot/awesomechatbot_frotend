package com.example.awesomechatbot

data class RecordResult(val recordList: ArrayList<RecordItem>)
data class RecordModel(val recordItem: RecordItem)
data class Message(val message: String?= null)
data class LoginResult(var email: String, var password : String, var name: String, var number : String, var birth : String)
