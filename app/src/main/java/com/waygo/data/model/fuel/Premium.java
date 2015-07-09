package com.waygo.data.model.fuel;


import android.support.annotation.NonNull;

import com.waygo.utilskt.Result;


public final class Premium extends Fuel {
    protected Premium(float value) {
        super(value);
    }

    @NonNull
    public static Result<Fuel> create(final float value) {
        return createValue(value).map(v -> (Fuel) new Premium(v));
    }
}
