package com.waygo.pojo.flightstatus

import com.google.gson.annotations.SerializedName

public data class ScheduledTimeLocal(
        SerializedName("DateTime")
        public val dateTime: String?)