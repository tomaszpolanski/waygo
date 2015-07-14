package com.waygo.pojo.flightstatus

import com.google.gson.annotations.SerializedName

public data class Flight(
        SerializedName("Departure")
        public val departure: Departure,
        SerializedName("Arrival")
        public val arrival: Arrival,
        SerializedName("MarketingCarrier")
        public val marketingCarrier: MarketingCarrier,
        SerializedName("OperatingCarrier")
        public val operatingCarrier: OperatingCarrier,
        SerializedName("Equipment")
        public val equipment: Equipment,
        SerializedName("FlightStatus")
        public val flightStatus: FlightStatus){

    public fun getId(): String {
        val date = departure.scheduledTimeUTC!!.dateTime?.substring(0, "yyyy-MM-dd".length())?.replace("-", "")
        val flightNumber = marketingCarrier.airlineID + marketingCarrier.flightNumber
        return flightNumber + date
    }
}
