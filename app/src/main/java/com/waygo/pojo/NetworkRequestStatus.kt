package com.waygo.pojo

import kotlin.platform.platformStatic

public data class NetworkRequestStatus private constructor(public val uri: String?,
                                                           public val status: String?,
                                                           public val errorCode: Int,
                                                           public val errorMessage: String?) {

    public fun isOngoing(): Boolean = status == NETWORK_STATUS_ONGOING
    public fun isError(): Boolean = status == NETWORK_STATUS_ERROR
    public fun isCompleted(): Boolean = status == NETWORK_STATUS_COMPLETED

    companion object {
        private val NETWORK_STATUS_ONGOING = "networkStatusOngoing"
        private val NETWORK_STATUS_ERROR = "networkStatusError"
        private val NETWORK_STATUS_COMPLETED = "networkStatusCompleted"

        public platformStatic fun ongoing(uri: String): NetworkRequestStatus =
                NetworkRequestStatus(uri, NETWORK_STATUS_ONGOING, 0, null)

        public platformStatic fun error(uri: String, errorCode: Int, errorMessage: String): NetworkRequestStatus =
                NetworkRequestStatus(uri, NETWORK_STATUS_ERROR, errorCode, errorMessage)
        
        public platformStatic fun completed(uri: String): NetworkRequestStatus =
                NetworkRequestStatus(uri, NETWORK_STATUS_COMPLETED, 0, null)
    }
}
