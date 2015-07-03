
package com.waygo.pojo.flightstatus;

import com.google.gson.annotations.SerializedName;

public class Flights {

    @SerializedName("Flight")
    private Flight mFlight;

    public Flight getFlight() {
        return mFlight;
    }

    public void setFlight(Flight flight) {
        this.mFlight = flight;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Flights{");
        sb.append("mFlight=").append(mFlight);
        sb.append('}');
        return sb.toString();
    }
}
