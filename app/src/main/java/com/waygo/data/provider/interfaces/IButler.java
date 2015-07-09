package com.waygo.data.provider.interfaces;

import android.support.annotation.NonNull;

import com.waygo.data.model.butler.ButlerResponse;
import com.waygo.utils.result.ResultJ;

import rx.Observable;

public interface IButler {

    @NonNull
    Observable<ResultJ<ButlerResponse>> ask(@NonNull final String question);
}
