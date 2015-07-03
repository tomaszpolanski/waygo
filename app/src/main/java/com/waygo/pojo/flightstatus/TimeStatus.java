
package com.waygo.pojo.flightstatus;

import com.google.gson.annotations.SerializedName;

public class TimeStatus {

    @SerializedName("Code")
    private final String mCode;

    @SerializedName("Definition")
    private final String mDefinition;

    public TimeStatus(String mCode, String mDefinition) {
        this.mCode = mCode;
        this.mDefinition = mDefinition;
    }

    public String getCode() {
        return mCode;
    }

    public String getDefinition() {
        return mDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeStatus)) {
            return false;
        }

        TimeStatus that = (TimeStatus) o;

        if (mCode != null ? !mCode.equals(that.mCode) : that.mCode != null) {
            return false;
        }
        return !(mDefinition != null ? !mDefinition.equals(that.mDefinition)
                : that.mDefinition != null);

    }

    @Override
    public int hashCode() {
        int result = mCode != null ? mCode.hashCode() : 0;
        result = 31 * result + (mDefinition != null ? mDefinition.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TimeStatus{");
        sb.append("mCode='").append(mCode).append('\'');
        sb.append(", mDefinition='").append(mDefinition).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
