
package com.waygo.pojo.flightstatus;

import com.google.gson.annotations.SerializedName;

public class ActualTimeUTC {

    @SerializedName("DateTime")
    private final String mDateTime;

    public ActualTimeUTC(String mDateTime) {
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
        if (!(o instanceof ActualTimeUTC)) {
            return false;
        }

        ActualTimeUTC that = (ActualTimeUTC) o;

        return !(mDateTime != null ? !mDateTime.equals(that.mDateTime) : that.mDateTime != null);

    }

    @Override
    public int hashCode() {
        return mDateTime != null ? mDateTime.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ActualTimeUTC{");
        sb.append("mDateTime='").append(mDateTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
