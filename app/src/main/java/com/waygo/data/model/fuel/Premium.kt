package com.waygo.data.model.fuel

import com.waygo.utilskt.Result
import kotlin.platform.platformStatic

public class Premium protected constructor(value: Float) : Fuel(value) {
    companion object {
        public platformStatic fun create(value: Float): Result<Fuel> =
                Fuel.createValue(value)
                        .map({ Premium })
                        .ofType(javaClass<Fuel>())
    }
}
