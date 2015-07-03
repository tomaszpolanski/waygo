
package com.waygo.pojo.flightstatus;

import com.google.gson.annotations.SerializedName;

public class ScheduledTimeUTC {

    @SerializedName("DateTime")
    private final String mDateTime;

    public ScheduledTimeUTC(String dateTime) {
        this.mDateTime = dateTime;
    }

    public String getDateTime() {
        return mDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScheduledTimeUTC)) {
            return false;
        }

        ScheduledTimeUTC that = (ScheduledTimeUTC) o;

        return !(mDateTime != null ? !mDateTime.equals(that.mDateTime) : that.mDateTime != null);

    }

    @Override
    public int hashCode() {
        return mDateTime != null ? mDateTime.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ScheduledTimeUTC{");
        sb.append("mDateTime='").append(mDateTime).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
