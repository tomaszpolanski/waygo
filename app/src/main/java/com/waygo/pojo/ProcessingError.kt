package com.waygo.pojo

import com.google.gson.annotations.SerializedName

public data class ProcessingError(
        SerializedName("Type")
        public val type: String?,
        SerializedName("Description")
        public val description: String?) {
}
