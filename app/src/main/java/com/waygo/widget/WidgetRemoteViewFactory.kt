package com.waygo.widget

import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService

import com.waygo.R

public class WidgetRemoteViewFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {
    override fun onDataSetChanged() = Unit
    override fun onDestroy()  = Unit
    override fun onCreate()  = Unit


    override fun getCount(): Int = 1
    override fun getViewAt(position: Int): RemoteViews {
        Log.d(TAG, "getViewAt(" + position + ")")
        return RemoteViews(context.getPackageName(), R.layout.widget_layout)
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int  = 0
    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false

    companion object {
        private val TAG = javaClass<WidgetRemoteViewFactory>().getSimpleName()
    }
}
