package com.waygo.pojo.flightstatus

import com.google.gson.annotations.SerializedName

import com.waygo.pojo.ProcessingErrors

public open class BaseLufthansaResponse(
        SerializedName("ProcessingErrors")
        public val processingErrors: ProcessingErrors?) {

    public fun isError(): Boolean = processingErrors != null
}
