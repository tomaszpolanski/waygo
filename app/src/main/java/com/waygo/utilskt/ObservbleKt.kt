package com.waygo.utilskt

import rx.Observable

fun <IN, OUT> Observable<IN>.choose(selector: (IN) -> Option<OUT>): Observable<OUT> =
        this.map { selector(it) }
                .filter { it.isSome }
                .map { it.unsafe }