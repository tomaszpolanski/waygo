package com.waygo.data.model.butler


import com.waygo.utilskt.Result

public class ButlerSayResponse private constructor(message: String) : ButlerResponse(message) {
    companion object {

        public fun create(message: String): Result<ButlerSayResponse> {
            return ButlerResponse.Companion
                    .createMessage(message)
                    .map {ButlerSayResponse(it)}
        }
    }

}
