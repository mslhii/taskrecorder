package com.kritikalerror.domeatask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class ReadCalendar {
    public static ArrayList<String> nameOfEvent = new ArrayList<String>();
    public static ArrayList<String> startDates = new ArrayList<String>();
    public static ArrayList<String> endDates = new ArrayList<String>();
    public static ArrayList<String> descriptions = new ArrayList<String>();
    
    private static int calendarId = 0;
    
    final protected static String CALENDAR_URI = "content://com.android.calendar/events";

    public static ArrayList<String> readCalendarEvent(Context context) {
        Cursor cursor = context.getContentResolver()
                .query(
                        Uri.parse(CALENDAR_URI),
                        new String[] { "calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation" }, null, null, null);
        cursor.moveToFirst();
        // Get calendar name
        String cNames[] = new String[cursor.getCount()];

        // Get calendar ID
        nameOfEvent.clear();
        startDates.clear();
        endDates.clear();
        descriptions.clear();
        for (int i = 0; i < cNames.length; i++) {
        	calendarId = cursor.getInt(0);
        	
        	// If event name has "YES!" in it, it's ours
        	if(cursor.getString(1).indexOf("YES!") != 0)
        	{
	            nameOfEvent.add(cursor.getString(1));
	            startDates.add(getDate(Long.parseLong(cursor.getString(3))));
	            endDates.add(getDate(Long.parseLong(cursor.getString(4))));
	            descriptions.add(cursor.getString(2));
	            cNames[i] = cursor.getString(1);
        	}
            cursor.moveToNext();
        }
        return nameOfEvent;
    }
    
    public static void deleteAllEvents(Context context) {
    	ContentResolver resolver = context.getContentResolver();
    	Cursor cursor;
    	cursor = resolver.query(Uri.parse(CALENDAR_URI), new String[]{ "_id" }, "calendar_id=" + calendarId, null, null);
        while(cursor.moveToNext()) {
            long eventId = cursor.getLong(cursor.getColumnIndex("_id"));
            resolver.delete(ContentUris.withAppendedId(Uri.parse(CALENDAR_URI), eventId), null, null);
        }
        cursor.close();
    }

    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
