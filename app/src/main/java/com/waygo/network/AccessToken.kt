package com.waygo.network

import com.google.gson.annotations.SerializedName

public data class AccessToken(
        SerializedName("access_token")
        public val accessToken: String,
        SerializedName("token_type")
        public val tokenType: String,
        SerializedName("expires_in")
        public val expiresIn: Long?)