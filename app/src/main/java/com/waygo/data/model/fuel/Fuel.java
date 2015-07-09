package com.waygo.data.model.fuel;


import android.support.annotation.NonNull;

import com.waygo.data.model.BaseModel;
import com.waygo.utilskt.Result;

public abstract class Fuel extends BaseModel {

    private final float mValue;

    protected Fuel(final float value) {
        mValue = value;
    }

    public float getValue() {
        return mValue;
    }

    @NonNull
    protected static Result<Float> createValue(final float value) {
        return Result.ofObj(value)
                .filter(v -> v >= 0 && v <= 1,  "Fuel value is invalid: " );
    }
}
