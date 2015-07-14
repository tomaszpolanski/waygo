package com.waygo.data.provider

import com.waygo.data.base.contract.DatabaseContract
import com.waygo.data.base.provider.ContractContentProviderBase

public class LufthansaContentProvider : ContractContentProviderBase() {

    init {
        val flightStatusContract = FlightStatusContract()
        addDatabaseContract(flightStatusContract)
        addDatabaseRoute(LufthansaSingleRoute(flightStatusContract.getTableName()))

        val networkRequestStatusContract = NetworkRequestStatusContract()
        addDatabaseContract(networkRequestStatusContract)
        addDatabaseRoute(NetworkRequestStatusSingleRoute(networkRequestStatusContract.getTableName()))
    }

    override fun getProviderName(): String = PROVIDER_NAME
    override fun getDatabaseName(): String = DATABASE_NAME
    override fun getDatabaseVersion(): Int = DATABASE_VERSION

    companion object {
        public val PROVIDER_NAME: String = "com.waygo.data.provider.LufthansaContentProvider"
        private val DATABASE_NAME = "database"
        private val DATABASE_VERSION = 12
    }
}
