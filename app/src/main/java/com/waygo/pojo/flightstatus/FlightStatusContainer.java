
package com.waygo.pojo.flightstatus;

import com.google.gson.annotations.SerializedName;

import com.waygo.pojo.ProcessingErrors;

public class FlightStatusContainer extends BaseLufthansaResponse {

    @SerializedName("FlightStatusResource")
    private final FlightStatusResource mFlightStatusResource;

    public FlightStatusContainer(FlightStatusResource flightStatusResource,
                                 ProcessingErrors processingErrors) {
        super(processingErrors);
        mFlightStatusResource = flightStatusResource;
    }

    public FlightStatusResource getFlightStatusResource() {
        return mFlightStatusResource;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FlightStatusContainer{");
        sb.append("mFlightStatusResource=").append(mFlightStatusResource);
        sb.append("mProcessingErrors=").append(mProcessingErrors);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FlightStatusContainer)) {
            return false;
        }

        FlightStatusContainer that = (FlightStatusContainer) o;

        return !(mFlightStatusResource != null ? !mFlightStatusResource
                .equals(that.mFlightStatusResource)
                : that.mFlightStatusResource != null);

    }

    @Override
    public int hashCode() {
        return mFlightStatusResource != null ? mFlightStatusResource.hashCode() : 0;
    }

}
