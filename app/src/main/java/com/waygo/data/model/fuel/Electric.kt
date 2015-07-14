package com.waygo.data.model.fuel

import com.waygo.utilskt.Result
import kotlin.platform.platformStatic

public class Electric protected constructor(value: Float) : Fuel(value) {
    companion object {
        public platformStatic fun create(value: Float): Result<Fuel> =
                Fuel.createValue(value)
                    .map({Electric})
                    .ofType(javaClass<Fuel>())
    }
}
