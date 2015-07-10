package com.waygo.data.model.butler


import com.waygo.utilskt.Result
import kotlin.platform.platformStatic

public class ButlerSayResponse private constructor(message: String) : ButlerResponse(message) {
    companion object {

        public platformStatic fun create(message: String): Result<ButlerSayResponse> =
                ButlerResponse.createMessage(message)
                        .map { ButlerSayResponse(it) }
    }

}
