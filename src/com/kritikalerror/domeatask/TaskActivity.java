package com.kritikalerror.domeatask;

import java.util.Calendar;
import java.util.TimeZone;

import android.support.v7.app.ActionBarActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TaskActivity extends ActionBarActivity {
	
	private Calendar mCalendar; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		
		final String eventUriString = "content://com.android.calendar/events";
		
		mCalendar = Calendar.getInstance();
		
		//long start = 1297512000; // 2011-02-12 12h00
	    //long end = 1297515600;   // 2011-02-12 13h00
		
		long start = mCalendar.getTimeInMillis();
		long end = mCalendar.getTimeInMillis() +60*60*1000;

	    String title = "TEST ENTRY - DELETE ME!!"; 

	    ContentValues cvEvent = new ContentValues();
	    cvEvent.put("calendar_id", 1);
	    cvEvent.put("title", title);
	    cvEvent.put("dtstart", start );
	    //cvEvent.put("hasAlarm", 1);
	    cvEvent.put("dtend", end);
	    cvEvent.put("eventTimezone", TimeZone.getDefault().getID());

	    getContentResolver().insert(Uri.parse(eventUriString), cvEvent);
		
		//addCalendarEvent();
		finish();
	}
	
	public void addCalendarEvent()
	{
	    Calendar cal = Calendar.getInstance();     
	    Intent intent = new Intent(Intent.ACTION_EDIT);
	    intent.setType("vnd.android.cursor.item/event");
	    intent.putExtra("beginTime", cal.getTimeInMillis());
	    intent.putExtra("allDay", true);
	    intent.putExtra("rrule", "FREQ=YEARLY");
	    intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
	    intent.putExtra("title", "A Test Event from android app");
	    intent.putExtra("description", "A Test Description from android app");
	    intent.putExtra("eventLocation", "Geolocation");
	    startActivity(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
