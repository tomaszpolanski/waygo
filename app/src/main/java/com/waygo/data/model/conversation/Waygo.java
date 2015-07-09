package com.waygo.data.model.conversation;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.waygo.R;
import com.waygo.data.model.butler.ButlerResponse;
import com.waygo.data.model.butler.ButlerSayResponse;
import com.waygo.data.model.butler.ButlerShowResponse;
import com.waygo.utilskt.None;
import com.waygo.utilskt.Option;

import jet.runtime.typeinfo.JetValueParameter;
import kotlin.Function0;
import kotlin.Function1;

public class Waygo extends Person {

    @NonNull
    private final Option<Bitmap> mImage;

    public Waygo(@NonNull final ButlerResponse response) {
        super(response.getMessage(), getUserImage(response));
        mImage = getBitmap(response);
    }

    public Waygo(@NonNull final String message) {
        super(message, Option.ofObj(R.drawable.small_waygo));
        mImage = new None<>();
    }

    @NonNull
    public Option<Bitmap> getImage() {
        return mImage;
    }


    @NonNull
    private static Option<Bitmap> getBitmap(@NonNull final ButlerResponse response) {
        return Option.ofObj(response)
                .ofType(ButlerShowResponse.class)
                .map(ButlerShowResponse::getImage);
    }

    @NonNull
    private static Option<Integer> getUserImage(@NonNull final ButlerResponse response) {
        return Option.ofObj(response)
                     .ofType(ButlerSayResponse.class)
                .map(new Function1<ButlerSayResponse, Integer>() {
                    @Override
                    public Integer invoke(@JetValueParameter(name = "p1") ButlerSayResponse __) {
                        return R.drawable.small_waygo;
                    }
                })
                .orOption(new Function0<Option<Integer>>() {
                    @Override
                    public Option<Integer> invoke() {
                        return Option.ofObj(R.drawable.small_lady);
                    }
                });
    }
}
