
package com.waygo.pojo.flightstatus;

import com.google.gson.annotations.SerializedName;

public class EstimatedTimeUTC {

    @SerializedName("DateTime")
    private final String mDateTime;

    public EstimatedTimeUTC(String mDateTime) {
        this.mDateTime = mDateTime;
    }

    public String getDateTime() {
        return mDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstimatedTimeUTC)) {
            return false;
        }

        EstimatedTimeUTC that = (EstimatedTimeUTC) o;

        return !(mDateTime != null ? !mDateTime.equals(that.mDateTime) : that.mDateTime != null);

    }

    @Override
    public int hashCode() {
        return mDateTime != null ? mDateTime.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EstimatedTimeUTC{");
        sb.append("mDateTime='").append(mDateTime).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
