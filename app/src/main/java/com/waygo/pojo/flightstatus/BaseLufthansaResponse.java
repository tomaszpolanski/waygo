package com.waygo.pojo.flightstatus;

import com.google.gson.annotations.SerializedName;

import com.waygo.pojo.ProcessingErrors;

/**
 * Created by Pawel Polanski on 7/4/15.
 */
public class BaseLufthansaResponse {

    @SerializedName("ProcessingErrors")
    protected final ProcessingErrors mProcessingErrors;

    public BaseLufthansaResponse(ProcessingErrors processingErrors) {
        mProcessingErrors = processingErrors;
    }

    public ProcessingErrors getProcessingErrors() {
        return mProcessingErrors;
    }

    public boolean isError() {
        return mProcessingErrors != null;
    }
}
