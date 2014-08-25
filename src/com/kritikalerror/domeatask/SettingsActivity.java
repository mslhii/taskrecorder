package com.kritikalerror.domeatask;

import android.support.v7.app.ActionBarActivity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends ActionBarActivity {
	
	SharedPreferences mSharedPreferences;
	TextView mEventBox;
	TextView mIdBox;
	EditText mLabelBox;
	
	public static final String PREFERENCES = "TaskSettings";
	public static final String Event = "eventKey";
	public static final String Label = "labelKey";
	
	public static String ACTION_WIDGET_CONFIGURE = "WIDGET_CONFIGURED";
	int thisWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		mEventBox = (TextView) findViewById(R.id.editBox);
		mIdBox = (TextView) findViewById(R.id.widgetid);
		mLabelBox = (EditText) findViewById(R.id.editLabel);
		
		mSharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
		
		if (mSharedPreferences.contains(Event))
		{
			mEventBox.setText(mSharedPreferences.getString(Event, ""));
		}
		
		getIdOfCurrentWidget(savedInstanceState);
		mIdBox.setText("Widget ID is: " + thisWidgetId);
	}
	
	public void submitEventType(View view)
	{
		String eventSubmit  = mEventBox.getText().toString();
		Editor editor = mSharedPreferences.edit();
		editor.putString(Event, eventSubmit);
		editor.commit();
		
		Toast.makeText(this, "Event saved!", Toast.LENGTH_LONG).show();
	}
	
    public void updateWidget(View view) {
    	AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.widget_layout);
        remoteViews.setTextViewText(R.id.widgetlabel, mLabelBox.getText().toString().trim());
        
        // Commit results to shared preferences to retain label name
        String labelSubmit  = mLabelBox.getText().toString().trim();
		Editor editor = mSharedPreferences.edit();
		editor.putString(Label, labelSubmit);
		editor.commit();
        
        remoteViews.setOnClickPendingIntent(R.id.widget_button, buildButtonPendingIntent(this));
        // update this widget
        appWidgetManager.updateAppWidget(thisWidgetId, remoteViews);
        
        Toast.makeText(this, "Updating widget label!", Toast.LENGTH_SHORT).show();
    }
	
	public void finishSettings(View view)
	{
		setResultDataToWidget(RESULT_OK);
	}
	
	public void cancelSettings(View view)
	{
		setResultDataToWidget(RESULT_CANCELED);
	}
	
    private void setResultDataToWidget(int result) {
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, thisWidgetId);
        setResult(result, resultValue);
        
        finish();
    }
    
    public static PendingIntent buildButtonPendingIntent(Context context) {
		Intent intent = new Intent();
	    intent.setAction("com.kritikalerror.intent.action.ADD_EVENT");
	    return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}
    
    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
		ComponentName myWidget = new ComponentName(context, TaskWidgetProvider.class);
	    AppWidgetManager manager = AppWidgetManager.getInstance(context);
	    manager.updateAppWidget(myWidget, remoteViews);		
	}
	
	public void getIdOfCurrentWidget(Bundle savedInstanceState) 
	{		 
        Bundle extras = getIntent().getExtras();
        if (extras != null) 
        {
            thisWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            Toast.makeText(this, "Widget ID is: " + thisWidgetId, Toast.LENGTH_LONG).show();
        }
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

 
    public void saveToPreferences(String file_name, String data) {
        SharedPreferences myPrefs = getSharedPreferences("Data",
                MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putString(file_name, data);
        prefsEditor.commit();
    }
 
    public String getWidgetData(String file_name) {
        SharedPreferences myPrefs = getSharedPreferences("Data",
                MODE_WORLD_READABLE);
        return (myPrefs.getString(file_name, null));
    }
}
