package com.waygo.network;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;


public final class ServiceGenerator {

    private static final String TAG = ServiceGenerator.class.getSimpleName();

    // No need to instantiate this class.
    private ServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        return createService(serviceClass, baseUrl, null);
    }

    public static <S> S createService(Class<S> serviceClass,
                                      String baseUrl,
                                      final AccessToken accessToken) {

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setLog(new AndroidLog(TAG))
                .setClient(new OkClient(new OkHttpClient()));

        if (accessToken != null) {
            builder.setRequestInterceptor(request -> {
                request.addHeader("Accept", "application/json");
                // TODO: API returns "bearer" but just accepts "Bearer"
//                request.addHeader("Authorization", accessToken.getTokenType() + " " + accessToken.getAccessToken());
                request.addHeader("Authorization", "Bearer " + accessToken.getAccessToken());
            });
        }

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }
}
