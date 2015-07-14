package com.waygo.pojo.flightstatus

import com.google.gson.annotations.SerializedName

public data class EstimatedTimeLocal(
        SerializedName("DateTime")
        public val dateTime: String?)