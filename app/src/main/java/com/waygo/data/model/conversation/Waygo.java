package com.waygo.data.model.conversation;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.waygo.R;
import com.waygo.data.model.butler.ButlerResponse;
import com.waygo.data.model.butler.ButlerSayResponse;
import com.waygo.data.model.butler.ButlerShowResponse;
import com.waygo.utils.option.OptionJ;

public class Waygo extends Person {

    @NonNull
    private final OptionJ<Bitmap> mImage;

    public Waygo(@NonNull final ButlerResponse response) {
        super(response.getMessage(), getUserImage(response));
        mImage = getBitmap(response);
    }

    public Waygo(@NonNull final String message) {
        super(message, OptionJ.asOption(R.drawable.small_waygo));
        mImage = OptionJ.NONE_J;
    }

    @NonNull
    public OptionJ<Bitmap> getImage() {
        return mImage;
    }


    @NonNull
    private static OptionJ<Bitmap> getBitmap(@NonNull final ButlerResponse response) {
        return OptionJ.asOption(response)
                .ofType(ButlerShowResponse.class)
                .map(ButlerShowResponse::getImage);
    }

    @NonNull
    private static OptionJ<Integer> getUserImage(@NonNull final ButlerResponse response) {
        return OptionJ.asOption(response)
                .ofType(ButlerSayResponse.class)
                .map(__ -> R.drawable.small_waygo)
                .orOption(() -> OptionJ.asOption(R.drawable.small_lady));
    }
}
