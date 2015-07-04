package com.waygo.data.model.butler;

import android.support.annotation.NonNull;

import com.waygo.utils.result.Result;

public abstract class ButlerResponse {

    @NonNull
    private final String mMessage;

    protected ButlerResponse(@NonNull final String message) {
        mMessage = message;
    }

    @NonNull
    public String getMessage() {
        return mMessage;
    }

    @NonNull
    protected static Result<String> createMessage(@NonNull final String message) {
        return Result.asResult(message)
                .filter(s -> !s.isEmpty(), __ -> "Sorry, didn't get that.");
    }
}
