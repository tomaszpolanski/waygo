
package com.waygo.pojo.flightstatus;

import com.google.gson.annotations.SerializedName;

public class Terminal {

    @SerializedName("Gate")
    private final String mGate;

    public Terminal(String mGate) {
        this.mGate = mGate;
    }

    public String getGate() {
        return mGate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Terminal)) {
            return false;
        }

        Terminal terminal = (Terminal) o;

        return !(mGate != null ? !mGate.equals(terminal.mGate) : terminal.mGate != null);
    }

    @Override
    public int hashCode() {
        return mGate != null ? mGate.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Terminal{");
        sb.append("mGate='").append(mGate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
