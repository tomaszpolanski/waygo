package com.waygo.data.model

import com.waygo.utilskt.Option

public data class GeoCoordinate private constructor(public val latitude: Double, public val longitude: Double) {

    public fun withLatitude(latitude: Double): Option<GeoCoordinate> {
        return create(latitude, longitude)
    }

    public fun withLongitude(longitude: Double): Option<GeoCoordinate> {
        return create(latitude, longitude)
    }

    companion object {

        public fun create(latitude: Double, longitude: Double): Option<GeoCoordinate> {
            return Option.ofObj(latitude)
                    .filter { lat -> Math.abs(lat) <= 90.0 }
                    .flatMap { lat ->
                        Option.ofObj(longitude)
                                .filter { lng -> Math.abs(lng) <= 180.0 }
                                .map { lng -> GeoCoordinate(lat, lng) }
                    }
        }
    }
}