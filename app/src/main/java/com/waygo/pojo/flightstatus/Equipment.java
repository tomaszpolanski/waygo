
package com.waygo.pojo.flightstatus;

import com.google.gson.annotations.SerializedName;

public class Equipment {

    @SerializedName("AircraftCode")
    private final int mAircraftCode;

    public Equipment(int mAircraftCode) {
        this.mAircraftCode = mAircraftCode;
    }

    public int getAircraftCode() {
        return mAircraftCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipment)) {
            return false;
        }

        Equipment equipment = (Equipment) o;

        return mAircraftCode == equipment.mAircraftCode;

    }

    @Override
    public int hashCode() {
        return mAircraftCode;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Equipment{");
        sb.append("mAircraftCode=").append(mAircraftCode);
        sb.append('}');
        return sb.toString();
    }
}
