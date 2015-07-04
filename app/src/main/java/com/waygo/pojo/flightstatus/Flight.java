
package com.waygo.pojo.flightstatus;

import com.google.gson.annotations.SerializedName;

public class Flight {

    @SerializedName("Departure")
    private final Departure mDeparture;

    @SerializedName("Arrival")
    private final Arrival mArrival;

    @SerializedName("MarketingCarrier")
    private final MarketingCarrier mMarketingCarrier;

    @SerializedName("OperatingCarrier")
    private final OperatingCarrier mOperatingCarrier;

    @SerializedName("Equipment")
    private final Equipment mEquipment;

    @SerializedName("FlightStatus")
    private final FlightStatus mFlightStatus;

    public Flight(Departure departure,
                  Arrival arrival,
                  MarketingCarrier marketingCarrier,
                  OperatingCarrier operatingCarrier,
                  Equipment equipment,
                  FlightStatus flightStatus) {
        this.mDeparture = departure;
        this.mArrival = arrival;
        this.mMarketingCarrier = marketingCarrier;
        this.mOperatingCarrier = operatingCarrier;
        this.mEquipment = equipment;
        this.mFlightStatus = flightStatus;
    }

    public Departure getDeparture() {
        return mDeparture;
    }

    public Arrival getArrival() {
        return mArrival;
    }

    public MarketingCarrier getMarketingCarrier() {
        return mMarketingCarrier;
    }

    public OperatingCarrier getOperatingCarrier() {
        return mOperatingCarrier;
    }

    public Equipment getEquipment() {
        return mEquipment;
    }

    public FlightStatus getFlightStatus() {
        return mFlightStatus;
    }

    public String getId() {
        final String date = getDeparture().getScheduledTimeUTC().getDateTime()
                                          .substring(0, "yyyy-MM-dd".length()).replace("-", "");
        final String flightNumber = getMarketingCarrier().getAirlineID()
                                    + getMarketingCarrier().getFlightNumber();
        return flightNumber + date;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Flight{");
        sb.append("mDeparture=").append(mDeparture);
        sb.append(", mArrival=").append(mArrival);
        sb.append(", mMarketingCarrier=").append(mMarketingCarrier);
        sb.append(", mOperatingCarrier=").append(mOperatingCarrier);
        sb.append(", mEquipment=").append(mEquipment);
        sb.append(", mFlightStatus=").append(mFlightStatus);
        sb.append('}');
        return sb.toString();
    }
}
