package com.waygo.utils.option;

import android.support.annotation.NonNull;

import com.android.internal.util.Predicate;
import com.waygo.utils.Linq;
import com.waygo.utils.result.ResultJ;


import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.functions.Func4;

public final class SomeJ<T> extends OptionJ<T> {

    @NonNull
    private final T mValue;

    SomeJ(@NonNull final T value) {
        mValue = value;
    }

    @Override
    public void iter(@NonNull final Action1<T> action) {
        action.call(mValue);
    }

    @NonNull
    @Override
    public <OUT> OptionJ<OUT> map(@NonNull final Func1<T, OUT> f) {
        return OptionJ.asOption(f.call(mValue));
    }

    @NonNull
    @Override
    public <OUT> OptionJ<OUT> flatMap(@NonNull final Func1<T, OptionJ<OUT>> f) {
        return f.call(mValue);
    }

    @NonNull
    @Override
    public OptionJ<T> filter(@NonNull final Predicate<? super T> predicate) {
        return predicate.apply(mValue) ? this : NONE_J;
    }

    @NonNull
    @Override
    public OptionJ<T> orOption(@NonNull final Func0<OptionJ<T>> f) {
        return this;
    }

    @NonNull
    @Override
    public T orDefault(@NonNull final Func0<T> def) {
        return mValue;
    }

    @NonNull
    @Override
    public T getUnsafe() {
        return mValue;
    }

    @NonNull
    @Override
    public <OUT> OptionJ<OUT> ofType(@NonNull Class<OUT> type) {
        return type.isInstance(mValue) ? OptionJ.asOption(type.cast(mValue)) : OptionJ.NONE_J;
    }

    @NonNull
    @Override
    public <OUT> OUT match(@NonNull Func1<T, OUT> fSome,
                           @NonNull Func0<OUT> fNone) {
        return fSome.call(mValue);
    }

    @NonNull
    @Override
    public <IN, OUT2> OptionJ<OUT2> lift(@NonNull final OptionJ<IN> optionJ,
                                        @NonNull final Func2<T, IN, OUT2> f) {
        return optionJ.map(b -> f.call(mValue, b));
    }

    @NonNull
    @Override
    public <IN1, IN2, OUT> OptionJ<OUT> lift(@NonNull OptionJ<IN1> optionJ1,
                                            @NonNull OptionJ<IN2> optionJ2,
                                            @NonNull Func3<T, IN1, IN2, OUT> f) {
        return optionJ1.lift(optionJ2, (o1, o2) -> f.call(mValue, o1, o2));
    }

    @NonNull
    @Override
    public <IN1, IN2, IN3, OUT> OptionJ<OUT> lift(@NonNull OptionJ<IN1> optionJ1,
                                                 @NonNull OptionJ<IN2> optionJ2,
                                                 @NonNull OptionJ<IN3> optionJ3,
                                                 @NonNull Func4<T, IN1, IN2, IN3, OUT> f) {
        return optionJ1.lift(optionJ2, optionJ3, (o1, o2, o3) -> f.call(mValue, o1, o2, o3));
    }

    @Override
    public int hashCode() {
        return mValue.hashCode();
    }

    @NonNull
    @Override
    public ResultJ<T> toResult(@NonNull final String message) {
        return ResultJ.asResult(mValue);
    }

    @NonNull
    @Override
    public Linq<T> toLinq() {
        return Linq.create(mValue);
    }

    @SuppressWarnings("EqualsWhichDoecreatesntCheckParameterClass")
    @Override
    public boolean equals(final Object o) {
        return OptionJ.asOption(o)
                     .ofType(SomeJ.class)
                     .filter(some -> some.getUnsafe().equals(mValue)) != OptionJ.NONE_J;
    }

    @Override
    public String toString() {
        return mValue.toString();
    }
}