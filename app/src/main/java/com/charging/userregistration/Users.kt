package com.charging.userregistration

import java.net.URL

data class Users(
    var userId:String = "",
    var userName:String = "",
    var userAge:Int = 0,
    var userEmail:String = "",
    val url: String = ""
)
