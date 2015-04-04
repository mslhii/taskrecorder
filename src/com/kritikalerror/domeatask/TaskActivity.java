package com.kritikalerror.domeatask;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TaskActivity extends ActionBarActivity {
	
	private SharedPreferences mSharedPreferences;
	private TextView mEventBox;
	private Button mGatherButton;
	private Button mDeleteButton;
	private TextView mLogText;
	
	public static final String PREFERENCES = "TaskSettings";
	public static final String Event = "eventKey";
	
	public static String ACTION_WIDGET_CONFIGURE = "WIDGET_CONFIGURED";
	int thisWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	private ArrayList<String> mCalendarDates;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		
		mLogText = (TextView) findViewById(R.id.textView3);
		
		mGatherButton = (Button) findViewById(R.id.button1);
		mGatherButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	Toast.makeText(TaskActivity.this, "Gathering Logs!", Toast.LENGTH_SHORT).show();
            	mCalendarDates = ReadCalendar.readCalendarEvent(getApplicationContext());
            	mLogText.setText("Events found over all time: " + Integer.toString(mCalendarDates.size()));
            	
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
