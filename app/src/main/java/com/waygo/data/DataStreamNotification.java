package com.waygo.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import rx.android.internal.Preconditions;

public class DataStreamNotification<T> {

    private enum Type {
        FETCHING_START, FETCHING_ERROR, ON_NEXT
    }

    @NonNull
    final private Type type;
    final private T value;
    final private Throwable error;

    private DataStreamNotification(@NonNull Type type, T value, Throwable error) {
        Preconditions.checkNotNull(type, "Type cannot be null.");

        this.type = type;
        this.value = value;
        this.error = error;
    }

    @Nullable
    public T getValue() {
        return value;
    }

    @NonNull
    public static<T> DataStreamNotification<T> fetchingStart() {
        return new DataStreamNotification<>(Type.FETCHING_START, null, null);
    }

    @NonNull
    public static<T> DataStreamNotification<T> onNext(T value) {
        return new DataStreamNotification<>(Type.ON_NEXT, value, null);
    }

    @NonNull
    public static<T> DataStreamNotification<T> fetchingError(Throwable throwable) {
        return new DataStreamNotification<>(Type.FETCHING_ERROR, null, throwable);
    }

    public boolean isFetchingStart() {
        return type.equals(Type.FETCHING_START);
    }

    public boolean isOnNext() {
        return type.equals(Type.ON_NEXT);
    }

    public boolean isFetchingError() {
        return type.equals(Type.FETCHING_ERROR);
    }

    @Nullable
    public Throwable getError() {
        return error;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DataStreamNotification{");
        sb.append("type=").append(type);
        sb.append(", value=").append(value);
        sb.append(", error=").append(error);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataStreamNotification)) {
            return false;
        }

        DataStreamNotification<?> that = (DataStreamNotification<?>) o;

        if (type != that.type) {
            return false;
        }
        if (value != null ? !value.equals(that.value) : that.value != null) {
            return false;
        }
        return !(error != null ? !error.equals(that.error) : that.error != null);

    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (error != null ? error.hashCode() : 0);
        return result;
    }
}
