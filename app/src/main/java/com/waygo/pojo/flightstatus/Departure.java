
package com.waygo.pojo.flightstatus;

import com.google.gson.annotations.SerializedName;

public class Departure {

    @SerializedName("AirportCode")
    private final String mAirportCode;

    @SerializedName("ScheduledTimeLocal")
    private final ScheduledTimeLocal mScheduledTimeLocal;

    @SerializedName("ScheduledTimeUTC")
    private final ScheduledTimeUTC mScheduledTimeUTC;

    @SerializedName("ActualTimeLocal")
    private final ActualTimeLocal mActualTimeLocal;

    @SerializedName("ActualTimeUTC")
    private final ActualTimeUTC mActualTimeUTC;

    @SerializedName("TimeStatus")
    private final TimeStatus mTimeStatus;

    @SerializedName("Terminal")
    private final Terminal mTerminal;

    public Departure(String airportCode,
                     ScheduledTimeLocal scheduledTimeLocal,
                     ScheduledTimeUTC scheduledTimeUTC,
                     ActualTimeLocal actualTimeLocal,
                     ActualTimeUTC actualTimeUTC,
                     TimeStatus timeStatus,
                     Terminal terminal) {
        this.mAirportCode = airportCode;
        this.mScheduledTimeLocal = scheduledTimeLocal;
        this.mScheduledTimeUTC = scheduledTimeUTC;
        this.mActualTimeLocal = actualTimeLocal;
        this.mActualTimeUTC = actualTimeUTC;
        this.mTimeStatus = timeStatus;
        this.mTerminal = terminal;
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
    public String toString() {
        final StringBuffer sb = new StringBuffer("Departure{");
        sb.append("mAirportCode='").append(mAirportCode).append('\'');
        sb.append(", mScheduledTimeLocal=").append(mScheduledTimeLocal);
        sb.append(", mScheduledTimeUTC=").append(mScheduledTimeUTC);
        sb.append(", mActualTimeLocal=").append(mActualTimeLocal);
        sb.append(", mActualTimeUTC=").append(mActualTimeUTC);
        sb.append(", mTimeStatus=").append(mTimeStatus);
        sb.append(", mTerminal=").append(mTerminal);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Departure)) {
            return false;
        }

        Departure departure = (Departure) o;

        if (mAirportCode != null ? !mAirportCode.equals(departure.mAirportCode)
                : departure.mAirportCode != null) {
            return false;
        }
        if (mScheduledTimeLocal != null ? !mScheduledTimeLocal.equals(departure.mScheduledTimeLocal)
                : departure.mScheduledTimeLocal != null) {
            return false;
        }
        if (mScheduledTimeUTC != null ? !mScheduledTimeUTC.equals(departure.mScheduledTimeUTC)
                : departure.mScheduledTimeUTC != null) {
            return false;
        }
        if (mActualTimeLocal != null ? !mActualTimeLocal.equals(departure.mActualTimeLocal)
                : departure.mActualTimeLocal != null) {
            return false;
        }
        if (mActualTimeUTC != null ? !mActualTimeUTC.equals(departure.mActualTimeUTC)
                : departure.mActualTimeUTC != null) {
            return false;
        }
        if (mTimeStatus != null ? !mTimeStatus.equals(departure.mTimeStatus)
                : departure.mTimeStatus != null) {
            return false;
        }
        return !(mTerminal != null ? !mTerminal.equals(departure.mTerminal)
                : departure.mTerminal != null);

    }

    @Override
    public int hashCode() {
        int result = mAirportCode != null ? mAirportCode.hashCode() : 0;
        result = 31 * result + (mScheduledTimeLocal != null ? mScheduledTimeLocal.hashCode() : 0);
        result = 31 * result + (mScheduledTimeUTC != null ? mScheduledTimeUTC.hashCode() : 0);
        result = 31 * result + (mActualTimeLocal != null ? mActualTimeLocal.hashCode() : 0);
        result = 31 * result + (mActualTimeUTC != null ? mActualTimeUTC.hashCode() : 0);
        result = 31 * result + (mTimeStatus != null ? mTimeStatus.hashCode() : 0);
        result = 31 * result + (mTerminal != null ? mTerminal.hashCode() : 0);
        return result;
    }

}
