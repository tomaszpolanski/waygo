package com.waygo.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pawel Polanski on 7/3/15.
 */
public class AccessToken extends BaseResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private Long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    @Override
    public String toString() {

        if (super.getError() != null) {
            return "AccessToken{error='" + super.getError() + "'}";
        }

        return "AccessToken{" +
               "accessToken='" + accessToken + '\'' +
               ", tokenType='" + tokenType + '\'' +
               ", expiresIn=" + expiresIn +
               '}';
    }

}
