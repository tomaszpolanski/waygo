package com.waygo.pojo

import com.google.gson.annotations.SerializedName

public data class ProcessingErrors(
        SerializedName("ProcessingError")
        public val processingError: ProcessingError?) {
}
