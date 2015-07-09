package com.waygo.data.model.butler;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.waygo.utils.result.ResultJ;

public class ButlerShowResponse extends ButlerResponse {

    @NonNull
    private final Bitmap mImage;

    private ButlerShowResponse(@NonNull final String message,
                              @NonNull final Bitmap image) {
        super(message);
        mImage = image;
    }

    @NonNull
    public static ResultJ<ButlerResponse> create(@NonNull final String message,
                                                 @NonNull final Bitmap image) {
        return createMessage(message)
                .map(m -> new ButlerShowResponse(message, image));
    }

    @NonNull
    public Bitmap getImage() {
        return mImage;
    }
}
