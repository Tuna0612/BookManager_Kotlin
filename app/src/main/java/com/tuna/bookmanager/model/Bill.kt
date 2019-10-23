package com.tuna.bookmanager.model

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class Bill {
    @SuppressLint("SimpleDateFormat")
    var sdf = SimpleDateFormat("dd-MM-yyyy")
    var id:String = ""
    var date: Date? = null

    constructor()


    constructor(id: String, date: Date?) {
        this.id = id
        this.date = date
    }


    override fun toString(): String {
        return id+" | "+sdf.format(date)
    }
}