package com.waygo.widget

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews

import com.waygo.R
import com.waygo.WaygoApplication
import com.waygo.data.DataLayer
import com.waygo.data.DataStreamNotification
import com.waygo.pojo.flightstatus.Flight



import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

public class WidgetService : Service() {

    var mGetFlightStatus: DataLayer.GetFlightStatus? = null
        @Inject set



    private var subscriptions: CompositeSubscription? = null

    init {
        WaygoApplication.getInstance().getGraph().inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.hasExtra("widgetId")) {
            val appWidgetId = intent.getIntExtra("widgetId", 0)
            Log.d(TAG, "onStartCommand(" + appWidgetId + ")")
            updateWidget(appWidgetId)
        } else {
            Log.e(TAG, "onStartCommand(<no widgetId>)")
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun updateWidget(widgetId: Int) {
        val appWidgetManager = AppWidgetManager.getInstance(getApplicationContext())

        val remoteViews = RemoteViews(getApplication().getPackageName(), R.layout.widget_layout)
        remoteViews.setTextViewText(R.id.widget_layout_title, "Loading repository..")
        appWidgetManager.updateAppWidget(widgetId, remoteViews)

        clearSubscriptions()
        subscriptions?.add(mGetFlightStatus?.call("LH400", "2015-07-03")
                ?.map{it.getValue()}
                ?.subscribeOn(AndroidSchedulers.mainThread())
                ?.subscribe { remoteViews.setTextViewText(R.id.widget_layout_title, it.flightStatus.definition)
                             appWidgetManager.updateAppWidget(widgetId, remoteViews)})
    }

    private fun clearSubscriptions() {
        if (subscriptions != null) {
            subscriptions!!.clear()
        }
        subscriptions = CompositeSubscription()
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate")
        super.onCreate()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        private val TAG = javaClass<WidgetService>().getSimpleName()
    }
}
