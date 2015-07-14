package com.waygo.data.model.conversation

import android.text.format.DateFormat

import com.waygo.utilskt.Option

public abstract class Person protected constructor(public val sentence: String, public val userImage: Option<Int>) {

    public val time: String

    init {
        time = DateFormat.format("HH:mm:ss", java.util.Date()).toString()
    }
}
