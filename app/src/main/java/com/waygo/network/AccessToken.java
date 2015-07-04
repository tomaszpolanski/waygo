package com.waygo.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pawel Polanski on 7/3/15.
 */
public class AccessToken extends BaseResponse {

    @SerializedName("access_token")
    private final String accessToken;

    @SerializedName("token_type")
    private final String tokenType;

    @SerializedName("expires_in")
    private final Long expiresIn;

    public AccessToken(String accessToken, String tokenType, Long expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

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
