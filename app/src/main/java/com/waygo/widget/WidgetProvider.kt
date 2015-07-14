package com.waygo.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent

public class WidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        for (appWidgetId in appWidgetIds!!) {
            val intentService = Intent(context, javaClass<WidgetService>())
            intentService.setAction("refresh")
            intentService.putExtra("widgetId", appWidgetId)
            context!!.startService(intentService)
        }
    }
}
