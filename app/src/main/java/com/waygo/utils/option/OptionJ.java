package com.waygo.utils.option;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.internal.util.Predicate;
import com.waygo.utils.Linq;
import com.waygo.utils.result.ResultJ;

import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.functions.Func4;

public abstract class OptionJ<T> {

    @NonNull
    public static final NoneJ NONE_J = new NoneJ();

    public abstract void iter(@NonNull final Action1<T> action);

    @NonNull
    public abstract <OUT> OptionJ<OUT> map(@NonNull final Func1<T, OUT> selector);

    @NonNull
    public abstract <OUT> OptionJ<OUT> flatMap(@NonNull final Func1<T, OptionJ<OUT>> selector);

    @NonNull
    public abstract OptionJ<T> filter(@NonNull final Predicate<? super T> selector);

    @NonNull
    public abstract OptionJ<T> orOption(@NonNull final Func0<OptionJ<T>> f);

    @NonNull
    public abstract T orDefault(@NonNull final Func0<T> def);

    @NonNull
    public abstract T getUnsafe();

    @NonNull
    public abstract <OUT> OptionJ<OUT> ofType(@NonNull final Class<OUT> type);

    @NonNull
    public static <IN> OptionJ<IN> asOption(@Nullable final IN value) {
        return value == null ? OptionJ.NONE_J : new SomeJ(value);
    }

    @NonNull
    public static <OUT> OptionJ<OUT> tryAsOption(@NonNull final Func0<OUT> f) {
        try {
            return OptionJ.asOption(f.call());
        } catch (Exception e) {
            return NONE_J;
        }
    }

    @NonNull
    public abstract <OUT> OUT match(@NonNull final Func1<T, OUT> fSome,
                                    @NonNull final Func0<OUT> fNone);

    @NonNull
    public OptionJ<T> id() {
        return this;
    }

    @NonNull
    public abstract  <IN1, OUT> OptionJ<OUT> lift(@NonNull final OptionJ<IN1> optionJ1,
                                                 @NonNull final Func2<T, IN1, OUT> f);

    @NonNull
    public abstract <IN1, IN2, OUT> OptionJ<OUT> lift(@NonNull final OptionJ<IN1> optionJ1,
                                                     @NonNull final OptionJ<IN2> optionJ2,
                                                     @NonNull final Func3<T, IN1, IN2, OUT> f);

    @NonNull
    public abstract <IN1, IN2, IN3, OUT> OptionJ<OUT> lift(@NonNull final OptionJ<IN1> optionJ1,
                                                          @NonNull final OptionJ<IN2> optionJ2,
                                                          @NonNull final OptionJ<IN3> optionJ3,
                                                          @NonNull final Func4<T, IN1, IN2, IN3, OUT> f);

    @NonNull
    public OptionJ<T> log(@Nullable final String message) {
        Log.e(message, "" + message);
        return this;
    }

    @NonNull
    public abstract ResultJ<T> toResult(@NonNull final String message);

    @NonNull
    public abstract Linq<T> toLinq();
}

