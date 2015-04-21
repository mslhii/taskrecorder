package com.kritikalerror.domeatask;

import java.util.ArrayList;
import java.util.Calendar;

import android.support.v7.app.ActionBarActivity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TaskActivity extends ActionBarActivity {
	
	private SharedPreferences mSharedPreferences;
	private TextView mEventBox;
	private Button mGatherButton;
	private Button mDeleteButton;
	private Button mDatesButton;
	private TextView mLogText;
	
	private int mSelectedYear;
	private int mSelectedMonth;
	private int mSelectedDay;
	private long mStartGatherTime;
	
	public static final String PREFERENCES = "TaskSettings";
	public static final String Event = "eventKey";
	
	public static String ACTION_WIDGET_CONFIGURE = "WIDGET_CONFIGURED";
	int thisWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	private ArrayList<String> mCalendarDates;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		
		Calendar calendar = Calendar.getInstance();
    	mSelectedYear = calendar.get(Calendar.YEAR);
    	mSelectedMonth = calendar.get(Calendar.MONTH);
    	mSelectedDay = calendar.get(Calendar.DAY_OF_MONTH);
    	mStartGatherTime = 0;
		
		mLogText = (TextView) findViewById(R.id.textView3);
		
		mGatherButton = (Button) findViewById(R.id.button1);
		mGatherButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	Toast.makeText(TaskActivity.this, "Gathering Logs!", Toast.LENGTH_SHORT).show();
            	
            	if(mStartGatherTime == 0) {
	            	mCalendarDates = ReadCalendar.readCalendarEvent(getApplicationContext());
	            	mLogText.setText("Events found over all time: " + Integer.toString(mCalendarDates.size()));
            	}
            	else
            	{
            		mCalendarDates = ReadCalendar.readCalendarEvent(getApplicationContext(), mStartGatherTime, System.currentTimeMillis());
            		String displayDate = String.valueOf(mSelectedDay) + "/" + 
	    					String.valueOf(mSelectedMonth + 1) + "/" + String.valueOf(mSelectedYear);
	            	mLogText.setText("Events found since " + displayDate + ": " + Integer.toString(mCalendarDates.size()));
            	}
            	
            	//DEBUG ONLY, REMOVE LATER
            	for(String dates : mCalendarDates)
            	{
            		Log.e("CALENDAR", dates);
            	}
            }
        });
		
		mDeleteButton = (Button) findViewById(R.id.button2);
		mDeleteButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	Toast.makeText(TaskActivity.this, "Deleting Logs!", Toast.LENGTH_SHORT).show();
            	ReadCalendar.deleteAllEvents(getApplicationContext());
            }
        });
		
		mDatesButton = (Button) findViewById(R.id.button3);
		mDatesButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	Toast.makeText(TaskActivity.this, "Setting dates!", Toast.LENGTH_SHORT).show();
            	showDatePickerDialog(mSelectedYear, mSelectedMonth, mSelectedDay, mOnDateSetListener);
            }
        });
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
	
	private DatePickerDialog showDatePickerDialog(int initialYear, 
			int initialMonth, int initialDay, OnDateSetListener listener) {
		DatePickerDialog dialog = new DatePickerDialog(this, listener, initialYear, initialMonth, initialDay);
		dialog.show();
		return dialog;
	}
	
	private OnDateSetListener mOnDateSetListener = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			mSelectedDay = dayOfMonth;
			mSelectedMonth = monthOfYear;
			mSelectedYear = year;
			
			// Do some fuzzy math to fix these dates
			//String month = ((mSelectedMonth + 1) > 9) ? "" + (mSelectedMonth + 1) : "0" + (mSelectedMonth + 1);
			//String day = ((mSelectedDay) < 10) ? "0" + mSelectedDay: "" + mSelectedDay;
			//mSelectedMonth = Integer.getInteger(month);
			//mSelectedDay = Integer.getInteger(day);
			//Log.e("DATE1", month + "/" + day + "/" + mSelectedYear);
			
			mStartGatherTime = ReadCalendar.getLongDate(String.valueOf(mSelectedDay) + "/" + 
					String.valueOf(mSelectedMonth + 1) + "/" + String.valueOf(mSelectedYear));
			Log.e("DATE", String.valueOf(mSelectedYear) + String.valueOf(mSelectedMonth + 1) + String.valueOf(mSelectedDay));
		}
	};
}
