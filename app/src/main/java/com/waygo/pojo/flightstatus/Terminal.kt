package com.waygo.pojo.flightstatus

import com.google.gson.annotations.SerializedName

public data class Terminal(
        SerializedName("Gate")
        public val gate: String?)