package com.waygo.utils.result;

import android.support.annotation.NonNull;

import com.android.internal.util.Predicate;
import com.waygo.utils.option.OptionJ;

import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;

public final class FailureJ<A> extends ResultJ<A> {

    @NonNull
    private final String mFailureMessage;

    FailureJ(@NonNull final String value) {
        mFailureMessage = value;
    }

    @NonNull
    @Override
    public <OUT> ResultJ<OUT> map(@NonNull final Func1<A, OUT> f) {
        return failure(this.mFailureMessage);
    }

    @NonNull
    @Override
    public <OUT> ResultJ<OUT> flatMap(@NonNull final Func1<A, ResultJ<OUT>> f) {
        return failure(this.mFailureMessage);
    }

    @NonNull
    @Override
    public ResultJ<A> filter(@NonNull final Predicate<? super A> predicate,
                            @NonNull final Func1<A, String> failMessage) {
        return failure(this.mFailureMessage);
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @NonNull
    @Override
    public String getMessage() {
        return mFailureMessage;
    }

    @NonNull
    @Override
    public A getUnsafe() {
        throw new IllegalStateException();
    }

    @NonNull
    @Override
    public ResultJ<A> or(@NonNull final Func0<ResultJ<A>> f) {
        return f.call();
    }

    @NonNull
    @Override
    public OptionJ<A> asOption() {
        return OptionJ.NONE_J;
    }

    @Override
    public <OUT> OUT match(@NonNull final Func1<A, OUT> fSuccess,
                           @NonNull final Func0<OUT> fFailure) {
        return fFailure.call();
    }

    @NonNull
    @Override
    public <IN, OUT> ResultJ<OUT> lift(@NonNull final ResultJ<IN> resultJIn,
                                      @NonNull final Func2<A, IN, OUT> f) {
        return (ResultJ<OUT>) this;
    }

    @Override
    public String toString() {
        return "Failure: " + getMessage();
    }
}
