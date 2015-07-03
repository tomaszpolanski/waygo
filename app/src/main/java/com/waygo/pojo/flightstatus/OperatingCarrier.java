
package com.waygo.pojo.flightstatus;

import com.google.gson.annotations.SerializedName;

public class OperatingCarrier {

    @SerializedName("AirlineID")
    private final String mAirlineID;

    @SerializedName("FlightNumber")
    private final int mFlightNumber;

    public OperatingCarrier(String mAirlineID, int mFlightNumber) {
        this.mAirlineID = mAirlineID;
        this.mFlightNumber = mFlightNumber;
    }

    public String getAirlineID() {
        return mAirlineID;
    }

    public int getFlightNumber() {
        return mFlightNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperatingCarrier)) {
            return false;
        }

        OperatingCarrier that = (OperatingCarrier) o;

        if (mFlightNumber != that.mFlightNumber) {
            return false;
        }
        return !(mAirlineID != null ? !mAirlineID.equals(that.mAirlineID)
                : that.mAirlineID != null);

    }

    @Override
    public int hashCode() {
        int result = mAirlineID != null ? mAirlineID.hashCode() : 0;
        result = 31 * result + mFlightNumber;
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OperatingCarrier{");
        sb.append("mAirlineID='").append(mAirlineID).append('\'');
        sb.append(", mFlightNumber=").append(mFlightNumber);
        sb.append('}');
        return sb.toString();
    }

}
