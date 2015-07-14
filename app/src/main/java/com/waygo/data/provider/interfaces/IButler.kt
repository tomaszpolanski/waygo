package com.waygo.data.provider.interfaces


import com.waygo.data.model.butler.ButlerResponse
import com.waygo.utilskt.Result

import rx.Observable

public interface IButler {

    public fun ask(question: String): Observable<Result<ButlerResponse>>
}
