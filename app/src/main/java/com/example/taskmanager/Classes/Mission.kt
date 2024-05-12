package com.example.taskmanager.Classes

import java.util.Date

data class Mission(
    var title: String = "",
    var description: String = "",
    var dateTime: Date? = null,
    var priority: String = "",
    var userId:String ="",
    var taskId:String ="",
    var status:String = "pending"
)
