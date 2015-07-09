package com.waygo.data.model.butler

import com.waygo.utilskt.Result

public abstract class ButlerResponse protected constructor(public val message: String) {
    companion object {

        protected fun createMessage(message: String): Result<String> {
            return Result.ofObj(message)
                    .filter( {!it.isEmpty()})  {"Sorry, didn't get that."}
        }
    }
}
