package com.waygo.viewmodels;

import android.support.annotation.NonNull;
import android.util.Log;

import rx.subscriptions.CompositeSubscription;

abstract public class AbstractViewModel {
    private static final String TAG = AbstractViewModel.class.getSimpleName();
    private CompositeSubscription compositeSubscription;

    final public void subscribeToDataStore() {
        Log.v(TAG, "subscribeToDataStore");
        unsubscribeFromDataStore();
        compositeSubscription = new CompositeSubscription();
        subscribeToDataStoreInternal(compositeSubscription);
    }

    public void dispose() {
        Log.v(TAG, "dispose");

        if (compositeSubscription != null) {
            Log.e(TAG, "Disposing without calling unsubscribeFromDataStore first");

            // Unsubscribe in case we are still for some reason subscribed
            unsubscribeFromDataStore();
        }
    }

    abstract void subscribeToDataStoreInternal(@NonNull CompositeSubscription compositeSubscription);

    public void unsubscribeFromDataStore() {
        Log.v(TAG, "unsubscribeToDataStore");
        if (compositeSubscription != null) {
            compositeSubscription.clear();
            compositeSubscription = null;
        }
    }
}
