package com.waygo.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.waygo.R;
import com.waygo.WaygoApplication;
import com.waygo.data.DataLayer;
import com.waygo.data.DataStreamNotification;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class WidgetService extends Service {
    private static final String TAG = WidgetService.class.getSimpleName();

    @Inject
    DataLayer.GetFlightStatus mGetFlightStatus;

    private CompositeSubscription subscriptions;

    public WidgetService() {
        WaygoApplication.getInstance().getGraph().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra("widgetId")) {
            final int appWidgetId = intent.getIntExtra("widgetId", 0);
            Log.d(TAG, "onStartCommand(" + appWidgetId + ")");
            updateWidget(appWidgetId);
        } else {
            Log.e(TAG, "onStartCommand(<no widgetId>)");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWidget(final int widgetId) {
        AppWidgetManager appWidgetManager = AppWidgetManager
                .getInstance(getApplicationContext());

        RemoteViews remoteViews = new RemoteViews(getApplication().getPackageName(), R.layout.widget_layout);
        remoteViews.setTextViewText(R.id.widget_layout_title, "Loading repository..");
        appWidgetManager.updateAppWidget(widgetId, remoteViews);

        clearSubscriptions();
        subscriptions.add(
                mGetFlightStatus.call("LH400", "2015-07-03")
                                .map(DataStreamNotification::getValue)
                                .subscribeOn(AndroidSchedulers.mainThread())
                                .subscribe(flight -> {
                                    remoteViews.setTextViewText(R.id.widget_layout_title,
                                                                flight.getFlightStatus().getDefinition());
//                                    remoteViews.setTextViewText(R.id.widget_layout_stargazers,
//                                                                "stars: " + repository
//                                                                        .getStargazersCount());
//                                    remoteViews.setTextViewText(R.id.widget_layout_forks,
//                                                                "forks: " + repository
//                                                                        .getForksCount());
                                    appWidgetManager.updateAppWidget(widgetId, remoteViews);
                                })
                         );
    }

    private void clearSubscriptions() {
        if (subscriptions != null) {
            subscriptions.clear();
        }
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
