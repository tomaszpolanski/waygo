package com.waygo.data.model.butler

import android.graphics.Bitmap

import com.waygo.utilskt.Result

public class ButlerShowResponse private constructor(message: String, public val image: Bitmap) : ButlerResponse(message) {
    companion object {
        public fun create(message: String, image: Bitmap): Result<ButlerShowResponse> {
            return ButlerResponse.Companion
                    .createMessage(message)
                    .map { ButlerShowResponse(it, image)}
        }
    }
}
