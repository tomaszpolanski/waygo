package com.waygo.pojo.flightstatus

import com.google.gson.annotations.SerializedName

public data class Arrival(
        SerializedName("AirportCode")
        public val airportCode: String?,
        SerializedName("ScheduledTimeLocal")
        public val scheduledTimeLocal: ScheduledTimeLocal?,
        SerializedName("ScheduledTimeUTC")
        public val scheduledTimeUTC: ScheduledTimeUTC?,
        SerializedName("EstimatedTimeLocal")
        public val estimatedTimeLocal: EstimatedTimeLocal?,
        SerializedName("EstimatedTimeUTC")
        public val estimatedTimeUTC: EstimatedTimeUTC?,
        SerializedName("ActualTimeLocal")
        public val actualTimeLocal: ActualTimeLocal?,
        SerializedName("ActualTimeUTC")
        public val actualTimeUTC: ActualTimeUTC?,
        SerializedName("TimeStatus")
        public val timeStatus: TimeStatus?,
        SerializedName("Terminal")
        public val terminal: Terminal?)