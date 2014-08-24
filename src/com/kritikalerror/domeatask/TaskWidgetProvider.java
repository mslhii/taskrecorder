package com.kritikalerror.domeatask;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

public class TaskWidgetProvider extends AppWidgetProvider {
	
	private static SharedPreferences mSharedPreferences;
	
	private static final String PREFERENCES = "TaskSettings";
	private static final String Label = "labelKey";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) 
	{
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		remoteViews.setOnClickPendingIntent(R.id.widget_button, buildButtonPendingIntent(context));
		
		pushWidgetUpdate(context, remoteViews);
	}

	public static PendingIntent buildButtonPendingIntent(Context context) {
		Intent intent = new Intent();
	    intent.setAction("com.kritikalerror.intent.action.ADD_EVENT");
	    return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
		ComponentName myWidget = new ComponentName(context, TaskWidgetProvider.class);
		mSharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
		
		if (mSharedPreferences.contains(Label))
		{
			remoteViews.setTextViewText(R.id.widgetlabel, mSharedPreferences.getString(Label, ""));
		}
		
	    AppWidgetManager manager = AppWidgetManager.getInstance(context);
	    manager.updateAppWidget(myWidget, remoteViews);		
	}
}
