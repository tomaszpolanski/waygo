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

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is FlightStatusContainer) {
            return false
        }

        return !(if (flightStatusResource != null)
            flightStatusResource != other.flightStatusResource
        else
            other.flightStatusResource != null)

    }

    override fun hashCode(): Int {
        return if (flightStatusResource != null) flightStatusResource.hashCode() else 0
    }

}
