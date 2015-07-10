package com.waygo.data.model.butler

import com.waygo.utilskt.Result
import kotlin.platform.platformStatic

public abstract class ButlerResponse protected constructor(public val message: String) {
    companion object {

        protected platformStatic fun createMessage(message: String): Result<String> =
                Result.ofObj(message)
                    .filter( {!it.isEmpty()})  {"Sorry, didn't get that."}
    }
}
