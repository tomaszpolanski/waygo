
package com.waygo.pojo.flightstatus;

import com.google.gson.annotations.SerializedName;

public class FlightStatusContainer {

    @SerializedName("FlightStatusResource")
    private final FlightStatusResource mFlightStatusResource;

    public FlightStatusContainer(FlightStatusResource flightStatusResource) {
        this.mFlightStatusResource = flightStatusResource;
    }

    public FlightStatusResource getmFlightStatusResource() {
        return mFlightStatusResource;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FlightStatusContainer{");
        sb.append("mFlightStatusResource=").append(mFlightStatusResource);
        sb.append('}');
        return sb.toString();
    }
}
