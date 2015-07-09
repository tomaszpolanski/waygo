package com.waygo.data.model;

import android.support.annotation.NonNull;

import com.waygo.utils.option.OptionJ;
import com.waygo.utilskt.Option;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import jet.runtime.typeinfo.JetValueParameter;
import kotlin.jvm.functions.Function1;

public final class GeoCoordinate {
    public final double Latitude;
    public final double Longitude;

    private GeoCoordinate(final double latitude, final double longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    @NonNull
    public Option<GeoCoordinate> withLatitude(final double latitude) {
        return create(latitude, Longitude);
    }

    @NonNull
    public Option<GeoCoordinate> withLongitude(final double longitude) {
        return create(Latitude, longitude);
    }

    @NonNull
    public static Option<GeoCoordinate> create(final double latitude, final double longitude) {
        return Option.ofObj(latitude)
                .filter(new Function1<Double, Boolean>() {
                    @Override
                    public Boolean invoke(@JetValueParameter(name = "p1") Double lat) {
                        return Math.abs(lat) <= 90.0;
                    }
                })
                .flatMap(new Function1<Double, Option<GeoCoordinate>>() {
                    @Override
                    public Option<GeoCoordinate> invoke(@JetValueParameter(name = "p1") Double lat) {
                        return Option.ofObj(longitude)
                                     .filter(new Function1<Double, Boolean>() {
                                         @Override
                                         public Boolean invoke(@JetValueParameter(name = "p1") Double lng) {
                                             return Math.abs(lng) <= 180.0;
                                         }
                                     })
                                     .map(new Function1<Double, GeoCoordinate>() {
                                         @Override
                                         public GeoCoordinate invoke(@JetValueParameter(name = "p1") Double lng) {
                                             return new GeoCoordinate(lat, lng);
                                         }
                                     });
                    }
                });
    }



    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(final Object o) {
        final double difference = 0.0001;
        return Option.ofObj(o)
                .filter(new Function1<Object, Boolean>() {
                    @Override
                    public Boolean invoke(@JetValueParameter(name = "p1") Object obj) {
                        return obj instanceof GeoCoordinate;
                    }
                })
                .map(new Function1<Object, GeoCoordinate>() {
                    @Override
                    public GeoCoordinate invoke(@JetValueParameter(name = "p1") Object obj) {
                        return (GeoCoordinate) obj;
                    }
                })
                .filter(new Function1<GeoCoordinate, Boolean>() {
                    @Override
                    public Boolean invoke(@JetValueParameter(name = "p1") GeoCoordinate other) {
                        return areEqual(other.Longitude, Longitude, difference);
                    }
                })
                .filter(new Function1<GeoCoordinate, Boolean>() {
                    @Override
                    public Boolean invoke(@JetValueParameter(name = "p1") GeoCoordinate other) {
                        return areEqual(other.Latitude, Latitude, difference);
                    }
                })
                     .getIsSome();
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