package com.waygo.pojo.flightstatus

import com.google.gson.annotations.SerializedName

public data class MarketingCarrier(
        SerializedName("AirlineID")
        public val airlineID: String?,
        SerializedName("FlightNumber")
        public val flightNumber: Int)