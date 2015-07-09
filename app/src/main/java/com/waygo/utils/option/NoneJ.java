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

public final class NoneJ<T> extends OptionJ<T> {

    NoneJ() { }

    @Override
    public void iter(@NonNull final Action1<T> action) {
        // Do nothing
    }

    @NonNull
    @Override
    public <OUT> OptionJ<OUT> map(@NonNull final Func1<T, OUT> f) {
        return NONE_J;
    }

    @NonNull
    @Override
    public <OUT> OptionJ<OUT> flatMap(@NonNull final Func1<T, OptionJ<OUT>> f) {
        return NONE_J;
    }

    @NonNull
    @Override
    public OptionJ<T> filter(@NonNull final Predicate<? super T> predicate) {
        return NONE_J;
    }

    @NonNull
    @Override
    public OptionJ<T> orOption(@NonNull final Func0<OptionJ<T>> f) {
        return f.call();
    }

    @NonNull
    @Override
    public T orDefault(@NonNull final Func0<T> def) {
        return def.call();
    }

    @NonNull
    @Override
    public T getUnsafe() {
        throw new IllegalStateException();
    }

    @NonNull
    @Override
    public <OUT> OptionJ<OUT> ofType(@NonNull Class<OUT> type) {
        return NONE_J;
    }

    @NonNull
    @Override
    public <OUT> OUT match(@NonNull Func1<T, OUT> fSome,
                           @NonNull Func0<OUT> fNone) {
        return fNone.call();
    }

    @NonNull
    @Override
    public <IN, OUT> OptionJ<OUT> lift(@NonNull final OptionJ<IN> optionJB,
                                      @NonNull final Func2<T, IN, OUT> f) {
        return NONE_J;
    }

    @NonNull
    @Override
    public <IN1, IN2, OUT> OptionJ<OUT> lift(@NonNull OptionJ<IN1> optionJ1,
                                            @NonNull OptionJ<IN2> optionJ2,
                                            @NonNull Func3<T, IN1, IN2, OUT> f) {
        return NONE_J;
    }

    @NonNull
    @Override
    public <IN1, IN2, IN3, OUT> OptionJ<OUT> lift(@NonNull OptionJ<IN1> optionJ1,
                                                 @NonNull OptionJ<IN2> optionJ2,
                                                 @NonNull OptionJ<IN3> optionJ3,
                                                 @NonNull Func4<T, IN1, IN2, IN3, OUT> f) {
        return NONE_J;
    }

    @NonNull
    @Override
    public ResultJ<T> toResult(@NonNull final String message) {
        return ResultJ.failure(message);
    }

    @NonNull
    @Override
    public Linq<T> toLinq() {
        return Linq.empty();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
