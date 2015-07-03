
package com.waygo.pojo.flightstatus;

import com.google.gson.annotations.SerializedName;

public class FlightStatusResource {

    @SerializedName("Flights")
    private final Flights mFlights;

    public FlightStatusResource(Flights flights) {
        this.mFlights = flights;
    }

    public Flights getFlights() {
        return mFlights;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FlightStatusResource{");
        sb.append("mFlights=").append(mFlights);
        sb.append('}');
        return sb.toString();
    }

}
