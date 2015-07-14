package com.waygo.pojo.flightstatus

import com.google.gson.annotations.SerializedName

import com.waygo.pojo.ProcessingErrors

public class FlightStatusContainer(
        SerializedName("FlightStatusResource")
        public val flightStatusResource: FlightStatusResource?, processingErrors: ProcessingErrors) : BaseLufthansaResponse(processingErrors) {

    override fun toString(): String {
        val sb = StringBuffer("FlightStatusContainer{")
        sb.append("mFlightStatusResource=").append(flightStatusResource)
        sb.append("mProcessingErrors=").append(processingErrors)
        sb.append('}')
        return sb.toString()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is FlightStatusContainer) {
            return false
        }

        return !(if (flightStatusResource != null)
            flightStatusResource != o.flightStatusResource
        else
            o.flightStatusResource != null)

    }

    override fun hashCode(): Int {
        return if (flightStatusResource != null) flightStatusResource.hashCode() else 0
    }

}
