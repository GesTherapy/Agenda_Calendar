package com.DeezApps.Agenda_Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class DayAgenda extends Activity {

	static final String intentMonthField = "launchMonth";
	static final String intentYearField = "launchYear";
	static final String intentDateField = "launchDate";
	static final int intentNoMonth = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.day_agenda);
		
		Intent i = getIntent();
		int loaderMonth = i.getIntExtra(intentMonthField, intentNoMonth) + 1;	//Months start at 0
		int loaderYear = i.getIntExtra(intentYearField, intentNoMonth);
		int loaderDate = i.getIntExtra(intentDateField, intentNoMonth);
		TextView check = (TextView) findViewById(R.id.day_agenda_check);
		check.setText(loaderMonth+" / "+loaderDate+" / "
						+loaderYear+" chosen!");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.day_agenda, menu);
		return true;
	}

}
