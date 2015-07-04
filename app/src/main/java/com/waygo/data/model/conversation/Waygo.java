package com.waygo.data.model.conversation;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.waygo.R;
import com.waygo.data.model.butler.ButlerResponse;
import com.waygo.data.model.butler.ButlerSayResponse;
import com.waygo.data.model.butler.ButlerShowResponse;
import com.waygo.utils.option.Option;

public class Waygo extends Person {

    @NonNull
    private final Option<Bitmap> mImage;

    public Waygo(@NonNull final ButlerResponse response) {
        super(response.getMessage(), getUserImage(response));
        mImage = getBitmap(response);
    }

    public Waygo(@NonNull final String message) {
        super(message, Option.asOption(R.drawable.small_waygo));
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

    @NonNull
    private static Option<Integer> getUserImage(@NonNull final ButlerResponse response) {
        return Option.asOption(response)
                .ofType(ButlerSayResponse.class)
                .map(__ -> R.drawable.small_waygo)
                .orOption(() -> Option.asOption(R.drawable.small_lady));
    }
}
