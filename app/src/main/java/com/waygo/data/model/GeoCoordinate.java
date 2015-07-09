package com.waygo.data.model;

import android.support.annotation.NonNull;

import com.waygo.utils.option.OptionJ;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public final class GeoCoordinate {
    public final double Latitude;
    public final double Longitude;

    private GeoCoordinate(final double latitude, final double longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    @NonNull
    public OptionJ<GeoCoordinate> withLatitude(final double latitude) {
        return create(latitude, Longitude);
    }

    @NonNull
    public OptionJ<GeoCoordinate> withLongitude(final double longitude) {
        return create(Latitude, longitude);
    }

    @NonNull
    public static OptionJ<GeoCoordinate> create(final double latitude, final double longitude) {
        return OptionJ.asOption(latitude)
                .filter(lat -> Math.abs(lat) <= 90.0)
                .flatMap(lat -> OptionJ.asOption(longitude)
                        .filter(lng -> Math.abs(lng) <= 180.0)
                        .map(lng -> new GeoCoordinate(lat, lng)));
    }



    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(final Object o) {
        final double difference = 0.0001;
        return OptionJ.asOption(o)
                .filter(obj -> obj instanceof GeoCoordinate)
                .map(obj -> (GeoCoordinate) obj)
                .filter(other -> areEqual(other.Longitude, Longitude, difference))
                .filter(other -> areEqual(other.Latitude, Latitude, difference)) != OptionJ.NONE_J;
    }

    @Override
    public String toString() {
        final NumberFormat formatter = new DecimalFormat("#0.0000");
        return String.format("Latitude: %S, Longitude %S",
                formatter.format(Latitude),
                formatter.format(Longitude));
    }

    private static boolean areEqual(final double first, final double second, final double difference) {
        return Math.abs(Math.abs(first) - Math.abs(second)) <= difference;
    }
}