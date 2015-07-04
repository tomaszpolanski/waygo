package com.waygo.data.model.butler;


import android.support.annotation.NonNull;

import com.waygo.utils.result.Result;

public final class ButlerSayResponse extends ButlerResponse {

    private ButlerSayResponse(@NonNull final String message) {
        super(message);
    }

    @NonNull
    public static Result<ButlerResponse> create(@NonNull final String message) {
        return createMessage(message)
                .map(s -> new ButlerSayResponse(s));
    }

}
