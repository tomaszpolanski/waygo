package com.waygo.utils.result;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.internal.util.Predicate;
import com.waygo.utils.option.OptionJ;

import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;

public abstract class ResultJ<A> {

    @NonNull
    public abstract <OUT> ResultJ<OUT> map(@NonNull final Func1<A, OUT> f);

    @NonNull
    public abstract <OUT> ResultJ<OUT> flatMap(@NonNull final Func1<A, ResultJ<OUT>> f);

    @NonNull
    public abstract ResultJ<A> filter(@NonNull final Predicate<? super A> predicate,
                                     @NonNull final Func1<A, String> failMessage);

    public abstract boolean isSuccess();

    @NonNull
    public abstract String getMessage();

    @NonNull
    public abstract A getUnsafe();

    @NonNull
    public abstract ResultJ<A> or(@NonNull final Func0<ResultJ<A>> f);

    @NonNull
    public static <A> SuccessJ<A> success(@NonNull final A value) {
        return new SuccessJ(value);
    }

    @NonNull
    public static <A> FailureJ<A> failure(@NonNull final String failure) {
        return new FailureJ<>(failure);
    }

    @NonNull
    public static <A> ResultJ<A> asResult(@Nullable A value) {
        return asResult(value, "Object is null");
    }

    @NonNull
    public static <A> ResultJ<A> asResult(@Nullable final A value,
                                         @NonNull final String failMessage) {
        return asResult(OptionJ.asOption(value), failMessage);
    }

    @NonNull
    public static <A> ResultJ<A> asResult(@NonNull final OptionJ<A> value,
                                         @NonNull final String failMessage) {
        return value != OptionJ.NONE_J ? success(value.getUnsafe()) : failure(failMessage);
    }

    @NonNull
    public abstract OptionJ<A> asOption();

    @NonNull
    public static <A> ResultJ<A> tryAsResult(@NonNull final Func0<A> f) {
        try {
            return ResultJ.asResult(f.call());
        } catch (Exception e) {
            return ResultJ.failure("Result failed: " + e.toString());
        }
    }

    @Nullable
    public abstract <OUT> OUT match(@NonNull final Func1<A, OUT> fSuccess,
                                    @NonNull final Func0<OUT> fFailure);

    @NonNull
    public abstract <IN, OUT> ResultJ<OUT> lift(@NonNull final ResultJ<IN> resultJIn,
                                               @NonNull final Func2<A, IN, OUT> f);

    @NonNull
    public ResultJ<A> id() {
        return this;
    }
}