package com.waygo.pojo;

public class NetworkRequestStatus {
    private static final String NETWORK_STATUS_ONGOING = "networkStatusOngoing";
    private static final String NETWORK_STATUS_ERROR = "networkStatusError";
    private static final String NETWORK_STATUS_COMPLETED = "networkStatusCompleted";

    private final String uri;
    private final String status;
    private final int errorCode;
    private final String errorMessage;

    private NetworkRequestStatus(String uri,
                                 String status,
                                 int errorCode,
                                 String errorMessage) {
        this.uri = uri;
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getUri() {
        return uri;
    }

    public String getStatus() {
        return status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static NetworkRequestStatus ongoing(String uri) {
        return new NetworkRequestStatus(uri, NETWORK_STATUS_ONGOING, 0, null);
    }

    public static NetworkRequestStatus error(String uri, int errorCode, String errorMessage) {
        return new NetworkRequestStatus(uri, NETWORK_STATUS_ERROR, 0, errorMessage);
    }

    public static NetworkRequestStatus completed(String uri) {
        return new NetworkRequestStatus(uri, NETWORK_STATUS_COMPLETED, 0, null);
    }

    public boolean isOngoing() {
        return status.equals(NETWORK_STATUS_ONGOING);
    }

    public boolean isError() {
        return status.equals(NETWORK_STATUS_ERROR);
    }

    public boolean isCompleted() {
        return status.equals(NETWORK_STATUS_COMPLETED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NetworkRequestStatus)) {
            return false;
        }

        NetworkRequestStatus that = (NetworkRequestStatus) o;

        if (errorCode != that.errorCode) {
            return false;
        }
        if (uri != null ? !uri.equals(that.uri) : that.uri != null) {
            return false;
        }
        if (status != null ? !status.equals(that.status) : that.status != null) {
            return false;
        }
        return !(errorMessage != null ? !errorMessage.equals(that.errorMessage)
                : that.errorMessage != null);

    }

    @Override
    public int hashCode() {
        int result = uri != null ? uri.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + errorCode;
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NetworkRequestStatus{");
        sb.append("uri='").append(uri).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", errorCode=").append(errorCode);
        sb.append(", errorMessage='").append(errorMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
