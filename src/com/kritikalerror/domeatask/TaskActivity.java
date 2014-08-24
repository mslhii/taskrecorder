package com.kritikalerror.domeatask;

import android.support.v7.app.ActionBarActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TaskActivity extends ActionBarActivity {
	
	SharedPreferences mSharedPreferences;
	TextView mEventBox;
	
	public static final String PREFERENCES = "TaskSettings";
	public static final String Event = "eventKey";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		
		mEventBox = (TextView) findViewById(R.id.editBox);
		
		mSharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
		
		if (mSharedPreferences.contains(Event))
		{
			mEventBox.setText(mSharedPreferences.getString(Event, ""));
		}
		
		// This code doesn't allow me to programmatically add a widget to the home screen
		//Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
	    //pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 1);
	    //startActivityForResult(pickIntent, 2);
	}
	
	public void submitEventType(View view)
	{
		String eventSubmit  = mEventBox.getText().toString();
		Editor editor = mSharedPreferences.edit();
		editor.putString(Event, eventSubmit);
		editor.commit();
		
		Toast.makeText(this, "Event saved!", Toast.LENGTH_LONG).show();
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
