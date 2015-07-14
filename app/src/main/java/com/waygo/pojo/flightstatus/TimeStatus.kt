package com.waygo.pojo.flightstatus

import com.google.gson.annotations.SerializedName

public data class TimeStatus(
        SerializedName("Code")
        public val code: String?,
        SerializedName("Definition")
        public val definition: String?)