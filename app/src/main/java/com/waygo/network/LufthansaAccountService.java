package com.waygo.network;

import com.waygo.pojo.flightstatus.FlightStatusContainer;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Pawel Polanski on 7/3/15.
 */
public interface LufthansaAccountService {

    String BASE_URL = "https://api.lufthansa.com/v1";

    @POST("/oauth/token")
    @FormUrlEncoded
    Observable<AccessToken> getAccessToken(@Field("client_id") String clientId,
                                           @Field("client_secret") String clientSecret,
                                           @Field("grant_type") String grantType);

    @GET("/operations/flightstatus/{flightNumber}/{date}")
    Observable<FlightStatusContainer> getFlightStatus(@Path("flightNumber") String flightNumber,
                                                      @Path("date") String date);

}