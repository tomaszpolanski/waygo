package com.waygo.utils.result;

import android.support.annotation.NonNull;

import com.android.internal.util.Predicate;
import com.waygo.utils.option.OptionJ;

import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;

public final class SuccessJ<A> extends ResultJ<A> {

    @NonNull
    private final A mValue;

    SuccessJ(@NonNull A value) {
        mValue = value;
    }

    @NonNull
    @Override
    public <OUT> ResultJ<OUT> map(@NonNull final Func1<A, OUT> f) {
        return success(f.call(mValue));
    }

    @NonNull
    @Override
    public <OUT> ResultJ<OUT> flatMap(@NonNull final Func1<A, ResultJ<OUT>> f) {
        return f.call(mValue);
    }

    @NonNull
    @Override
    public ResultJ<A> filter(@NonNull final Predicate<? super A> predicate,
                            @NonNull final Func1<A, String> failMessage) {
        return predicate.apply(mValue) ? this : failure(failMessage.call(mValue));
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @NonNull
    @Override
    public String getMessage() {
        throw new IllegalStateException();
    }

    @NonNull
    @Override
    public A getUnsafe() {
        return mValue;
    }

    @NonNull
    @Override
    public ResultJ<A> or(@NonNull final Func0<ResultJ<A>> f) {
        return this;
    }

    @NonNull
    @Override
    public OptionJ<A> asOption() {
        return OptionJ.asOption(mValue);
    }

    @Override
    public <OUT> OUT match(@NonNull final Func1<A, OUT> fSuccess,
                           @NonNull final Func0<OUT> fFailure) {
        return fSuccess.call(mValue);
    }

    @NonNull
    @Override
    public <IN, OUT> ResultJ<OUT> lift(@NonNull final ResultJ<IN> resultJIn,
                                      @NonNull final Func2<A, IN, OUT> f) {
        return resultJIn.map(b -> f.call(mValue, b));
    }

    @Override
    public String toString() {
        return "Success: " + mValue;
    }
}
