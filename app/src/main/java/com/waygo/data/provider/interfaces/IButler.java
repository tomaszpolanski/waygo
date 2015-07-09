package com.waygo.data.provider.interfaces;

import android.support.annotation.NonNull;

import com.waygo.data.model.butler.ButlerResponse;
import com.waygo.utilskt.Result;

import rx.Observable;

public interface IButler {

    @NonNull
    Observable<Result<ButlerResponse>> ask(@NonNull final String question);
}
