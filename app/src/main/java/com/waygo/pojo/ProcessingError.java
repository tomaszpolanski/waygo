
package com.waygo.pojo;

import com.google.gson.annotations.SerializedName;

public class ProcessingError {

    @SerializedName("Type")
    private final String mType;

    @SerializedName("Description")
    private final String mDescription;

    public ProcessingError(String type, String description) {
        mType = type;
        mDescription = description;
    }

    public String getType() {
        return mType;
    }

    public String getDescription() {
        return mDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessingError)) {
            return false;
        }

        ProcessingError that = (ProcessingError) o;

        if (mType != null ? !mType.equals(that.mType) : that.mType != null) {
            return false;
        }
        return !(mDescription != null ? !mDescription.equals(that.mDescription)
                : that.mDescription != null);

    }

    @Override
    public int hashCode() {
        int result = mType != null ? mType.hashCode() : 0;
        result = 31 * result + (mDescription != null ? mDescription.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProcessingError{");
        sb.append("mType='").append(mType).append('\'');
        sb.append(", mDescription='").append(mDescription).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
