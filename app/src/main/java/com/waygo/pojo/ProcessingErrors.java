
package com.waygo.pojo;

import com.google.gson.annotations.SerializedName;

public class ProcessingErrors {

    @SerializedName("ProcessingError")
    private final ProcessingError mProcessingError;

    public ProcessingErrors(ProcessingError processingError) {
        mProcessingError = processingError;
    }

    public ProcessingError getProcessingError() {
        return mProcessingError;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessingErrors)) {
            return false;
        }

        ProcessingErrors that = (ProcessingErrors) o;

        return !(mProcessingError != null ? !mProcessingError.equals(that.mProcessingError)
                : that.mProcessingError != null);

    }

    @Override
    public int hashCode() {
        return mProcessingError != null ? mProcessingError.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProcessingErrors{");
        sb.append("mProcessingError=").append(mProcessingError);
        sb.append('}');
        return sb.toString();
    }
}
