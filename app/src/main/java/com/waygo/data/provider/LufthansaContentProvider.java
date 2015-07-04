package com.waygo.data.provider;

import com.waygo.data.base.contract.DatabaseContract;
import com.waygo.data.base.provider.ContractContentProviderBase;

import android.support.annotation.NonNull;

public class LufthansaContentProvider extends ContractContentProviderBase {
    public static final String PROVIDER_NAME = "com.waygo.data.provider.LufthansaContentProvider";
    private static final String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 12;

    public LufthansaContentProvider() {
        DatabaseContract flightStatusContract = new FlightStatusContract();
        addDatabaseContract(flightStatusContract);
        addDatabaseRoute( new LufthansaSingleRoute(flightStatusContract.getTableName()));

        DatabaseContract networkRequestStatusContract = new NetworkRequestStatusContract();
        addDatabaseContract(networkRequestStatusContract);
        addDatabaseRoute(
                new NetworkRequestStatusSingleRoute(networkRequestStatusContract.getTableName()));
    }

    @NonNull
    @Override
    protected String getProviderName() {
        return PROVIDER_NAME;
    }

    @NonNull
    @Override
    protected String getDatabaseName() {
        return DATABASE_NAME;
    }

    @Override
    protected int getDatabaseVersion() {
        return DATABASE_VERSION;
    }
}
