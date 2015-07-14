package com.waygo.pojo.flightstatus

import com.google.gson.annotations.SerializedName

public data class FlightStatusResource(
        SerializedName("Flights")
        public val flights: Flights)