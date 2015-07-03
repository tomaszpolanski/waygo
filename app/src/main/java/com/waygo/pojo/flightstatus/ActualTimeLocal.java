
package com.waygo.pojo.flightstatus;

import com.google.gson.annotations.SerializedName;

public class ActualTimeLocal {

    @SerializedName("DateTime")
    private final String mDateType;

    public ActualTimeLocal(String mDateType) {
        this.mDateType = mDateType;
    }

    public String getDateTime() {
        return mDateType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActualTimeLocal)) {
            return false;
        }

        ActualTimeLocal that = (ActualTimeLocal) o;

        return !(mDateType != null ? !mDateType.equals(that.mDateType) : that.mDateType != null);

    }

    @Override
    public int hashCode() {
        return mDateType != null ? mDateType.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ActualTimeLocal{");
        sb.append("mDateType='").append(mDateType).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
