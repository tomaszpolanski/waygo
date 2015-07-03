package com.waygo.data.model.fuel;


import android.support.annotation.NonNull;

import com.waygo.utils.result.Result;

public final class Electric extends Fuel {
    protected Electric(float value) {
        super(value);
    }

    @NonNull
    public static Result<Fuel> create(final float value) {
        return createValue(value).map(v -> new Electric(v));
    }
}
