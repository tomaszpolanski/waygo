package com.waygo.data.model;


import android.support.annotation.NonNull;

import com.waygo.utils.Linq;
import com.waygo.utils.option.OptionJ;

import java.util.Arrays;



public abstract class BaseModel {

    @NonNull
    protected <T> OptionJ<T> cast(final Object o) {
        return OptionJ.asOption(o)
                .filter(obj -> this.getClass().isInstance(obj))
                .map(obj -> (T) obj.getClass().cast(obj));
    }

    protected String getString(final Object... params) {
        final String parameters =
                Linq.create(Arrays.asList(params).iterator())
                        .buffer(2, 2)
                        .map(pair -> String.format("%s = \"%s\"", pair.get(0), pair.get(1)))
                        .reduce((f, s) -> f + ", " + s);
        return String.format("%s = { %s }",
                this.getClass().getSimpleName(),
                parameters);
    }
}
