package com.waygo.data.model.conversation;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.waygo.data.model.butler.ButlerResponse;
import com.waygo.data.model.butler.ButlerShowResponse;
import com.waygo.utils.option.Option;

public class Waygo extends Person {

    @NonNull
    private final Option<Bitmap> mImage;

    public Waygo(@NonNull final ButlerResponse response) {
        super(response.getMessage());
        mImage = getBitmap(response);
    }

    public Waygo(@NonNull final String message) {
        super(message);
        mImage = Option.NONE;
    }

    @NonNull
    public Option<Bitmap> getImage() {
        return mImage;
    }


    @NonNull
    private static Option<Bitmap> getBitmap(@NonNull final ButlerResponse response) {
        return Option.asOption(response)
                .ofType(ButlerShowResponse.class)
                .map(ButlerShowResponse::getImage);
    }
}
