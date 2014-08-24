package com.kritikalerror.domeatask;

import java.util.Calendar;
import java.util.TimeZone;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

public class TaskWidgetIntentReceiver extends BroadcastReceiver {
	
	public static final String PREFERENCES = "TaskSettings";
	public static final String eventUriString = "content://com.android.calendar/events";
	public static final String Event = "eventKey";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals("com.kritikalerror.intent.action.ADD_EVENT")){
			updateWidgetPictureAndButtonListener(context);
		}
	}

	private void updateWidgetPictureAndButtonListener(Context context) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		
		// Add event to Calendar on user click
		addEventToCalendar(context);
				
		//REMEMBER TO ALWAYS REFRESH YOUR BUTTON CLICK LISTENERS!!!
		remoteViews.setOnClickPendingIntent(R.id.widget_button, TaskWidgetProvider.buildButtonPendingIntent(context));
		
		TaskWidgetProvider.pushWidgetUpdate(context.getApplicationContext(), remoteViews);
	}

	private void addEventToCalendar(Context context) {
		final long timeMsAdder = 1000; //1 hour is 60*60*1000, we need 1 second
		String eventString = "YES! ";
		
		SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
		if (sharedPreferences.contains(Event))
		{
			eventString = eventString + sharedPreferences.getString(Event, "");
		}
		
		Calendar calendar = Calendar.getInstance();
		
		long start = calendar.getTimeInMillis();
		long end = calendar.getTimeInMillis() + timeMsAdder;

	    ContentValues calEvent = new ContentValues();
	    calEvent.put("calendar_id", 1);
	    calEvent.put("title", eventString);
	    calEvent.put("dtstart", start);
	    calEvent.put("dtend", end);
	    calEvent.put("eventTimezone", TimeZone.getDefault().getID());

	    context.getContentResolver().insert(Uri.parse(eventUriString), calEvent);
	    
	    Toast.makeText(context, "Task recorded in calendar!", Toast.LENGTH_LONG).show();
	}
}
