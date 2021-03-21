package com.hossamelsharkawy.req.req

/**
 * Created by Hossam Elsharkawy
0201099197556
on 8/13/2018.  time :11:49

 */

data class Api_message(
    val message: String,
    val title: String)
open class Res {
    var api_status: String = ""
    var api_http: Int = -1
    val messages: Any = ""

    val  api_message :Api_message? =null

}

