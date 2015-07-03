
package com.waygo.pojo.flightstatus;

import com.google.gson.annotations.SerializedName;

public class Arrival {

    @SerializedName("AirportCode")
    private final String mAirportCode;

    @SerializedName("ScheduledTimeLocal")
    private final ScheduledTimeLocal mScheduledTimeLocal;

    @SerializedName("ScheduledTimeUTC")
    private final ScheduledTimeUTC mScheduledTimeUTC;

    @SerializedName("EstimatedTimeLocal")
    private final EstimatedTimeLocal mEstimatedTimeLocal;

    @SerializedName("EstimatedTimeUTC")
    private final EstimatedTimeUTC mEstimatedTimeUTC;

    @SerializedName("ActualTimeLocal")
    private final ActualTimeLocal mActualTimeLocal;

    @SerializedName("ActualTimeUTC")
    private final ActualTimeUTC mActualTimeUTC;

    @SerializedName("TimeStatus")
    private final TimeStatus mTimeStatus;

    @SerializedName("Terminal")
    private final Terminal mTerminal;

    public Arrival(String airportCode,
                   ScheduledTimeLocal mScheduledTimeLocal,
                   ScheduledTimeUTC mScheduledTimeUTC,
                   EstimatedTimeLocal mEstimatedTimeLocal,
                   EstimatedTimeUTC mEstimatedTimeUTC,
                   ActualTimeLocal mActualTimeLocal,
                   ActualTimeUTC mActualTimeUTC,
                   TimeStatus mTimeStatus,
                   Terminal mTerminal) {
        this.mAirportCode = airportCode;
        this.mScheduledTimeLocal = mScheduledTimeLocal;
        this.mScheduledTimeUTC = mScheduledTimeUTC;
        this.mEstimatedTimeLocal = mEstimatedTimeLocal;
        this.mEstimatedTimeUTC = mEstimatedTimeUTC;
        this.mActualTimeLocal = mActualTimeLocal;
        this.mActualTimeUTC = mActualTimeUTC;
        this.mTimeStatus = mTimeStatus;
        this.mTerminal = mTerminal;
    }

    public String getAirportCode() {
        return mAirportCode;
    }

    public ScheduledTimeLocal getScheduledTimeLocal() {
        return mScheduledTimeLocal;
    }

    public ScheduledTimeUTC getScheduledTimeUTC() {
        return mScheduledTimeUTC;
    }

    public EstimatedTimeLocal getEstimatedTimeLocal() {
        return mEstimatedTimeLocal;
    }

    public EstimatedTimeUTC getEstimatedTimeUTC() {
        return mEstimatedTimeUTC;
    }

    public ActualTimeLocal getActualTimeLocal() {
        return mActualTimeLocal;
    }

    public ActualTimeUTC getActualTimeUTC() {
        return mActualTimeUTC;
    }

    public TimeStatus getTimeStatus() {
        return mTimeStatus;
    }

    public Terminal getTerminal() {
        return mTerminal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Arrival)) {
            return false;
        }

        Arrival arrival = (Arrival) o;

        if (mAirportCode != null ? !mAirportCode.equals(arrival.mAirportCode)
                : arrival.mAirportCode != null) {
            return false;
        }
        if (mScheduledTimeLocal != null ? !mScheduledTimeLocal.equals(arrival.mScheduledTimeLocal)
                : arrival.mScheduledTimeLocal != null) {
            return false;
        }
        if (mScheduledTimeUTC != null ? !mScheduledTimeUTC.equals(arrival.mScheduledTimeUTC)
                : arrival.mScheduledTimeUTC != null) {
            return false;
        }
        if (mEstimatedTimeLocal != null ? !mEstimatedTimeLocal.equals(arrival.mEstimatedTimeLocal)
                : arrival.mEstimatedTimeLocal != null) {
            return false;
        }
        if (mEstimatedTimeUTC != null ? !mEstimatedTimeUTC.equals(arrival.mEstimatedTimeUTC)
                : arrival.mEstimatedTimeUTC != null) {
            return false;
        }
        if (mActualTimeLocal != null ? !mActualTimeLocal.equals(arrival.mActualTimeLocal)
                : arrival.mActualTimeLocal != null) {
            return false;
        }
        if (mActualTimeUTC != null ? !mActualTimeUTC.equals(arrival.mActualTimeUTC)
                : arrival.mActualTimeUTC != null) {
            return false;
        }
        if (mTimeStatus != null ? !mTimeStatus.equals(arrival.mTimeStatus)
                : arrival.mTimeStatus != null) {
            return false;
        }
        return !(mTerminal != null ? !mTerminal.equals(arrival.mTerminal)
                : arrival.mTerminal != null);

    }

    @Override
    public int hashCode() {
        int result = mAirportCode != null ? mAirportCode.hashCode() : 0;
        result = 31 * result + (mScheduledTimeLocal != null ? mScheduledTimeLocal.hashCode() : 0);
        result = 31 * result + (mScheduledTimeUTC != null ? mScheduledTimeUTC.hashCode() : 0);
        result = 31 * result + (mEstimatedTimeLocal != null ? mEstimatedTimeLocal.hashCode() : 0);
        result = 31 * result + (mEstimatedTimeUTC != null ? mEstimatedTimeUTC.hashCode() : 0);
        result = 31 * result + (mActualTimeLocal != null ? mActualTimeLocal.hashCode() : 0);
        result = 31 * result + (mActualTimeUTC != null ? mActualTimeUTC.hashCode() : 0);
        result = 31 * result + (mTimeStatus != null ? mTimeStatus.hashCode() : 0);
        result = 31 * result + (mTerminal != null ? mTerminal.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Arrival{");
        sb.append("mAirportCode='").append(mAirportCode).append('\'');
        sb.append(", mScheduledTimeLocal=").append(mScheduledTimeLocal);
        sb.append(", mScheduledTimeUTC=").append(mScheduledTimeUTC);
        sb.append(", mEstimatedTimeLocal=").append(mEstimatedTimeLocal);
        sb.append(", mEstimatedTimeUTC=").append(mEstimatedTimeUTC);
        sb.append(", mActualTimeLocal=").append(mActualTimeLocal);
        sb.append(", mActualTimeUTC=").append(mActualTimeUTC);
        sb.append(", mTimeStatus=").append(mTimeStatus);
        sb.append(", mTerminal=").append(mTerminal);
        sb.append('}');
        return sb.toString();
    }
}
