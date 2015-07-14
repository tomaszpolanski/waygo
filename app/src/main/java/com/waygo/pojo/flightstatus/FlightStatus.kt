package com.waygo.pojo.flightstatus

import com.google.gson.annotations.SerializedName

public data class FlightStatus(
        SerializedName("Code")
        public val code: String?,
        SerializedName("Definition")
        public val definition: String?)
