package com.DeezApps.Agenda_Calendar;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ViewMonth extends Activity  {

	private static final String lastRunVersion = "Version";

	static final String intentMonthField = "launchMonth";
	static final String intentYearField = "launchYear";
	static final int intentNoMonth = -1;
	
	static final String intentDateField = "launchDate";
	
	public static boolean on1=true, on2=true, on3=true, on4=true, on5=true, 
			on6=true, on7=true, on8=true, on9=true, on10=true;
	

	private OnClickListener monthRight = new OnClickListener() {

		public void onClick(View v) {

			CalendarView cv = (CalendarView) findViewById(R.id.view_month_calendar);
			cv.increaseMonth();

		}
	};

	private OnClickListener monthLeft = new OnClickListener() {

		public void onClick(View v) {

			CalendarView cv = (CalendarView) findViewById(R.id.view_month_calendar);
			cv.decreaseMonth();
		}
	};
	
/*	private OnClickListener DayPressListener = new OnClickListener() {

		public void onClick(View v) {
			
			DayView dV = (DayView) v;

			// If the day is legit ie. actually a square
			int date = dV.getDate();
			if (date != DayView.NO_DATE) {
				
				Intent i = new Intent();
				ComponentName actName;

				actName = new ComponentName("com.DeezApps.Agenda_Calendar",
						"com.DeezApps.Agenda_Calendar.DayAgenda");
				i.setComponent(actName);

				CalendarView cv = (CalendarView) findViewById(R.id.view_month_calendar);
				i.putExtra(DayAgenda.intentMonthField, cv.getMonth());
				i.putExtra(DayAgenda.intentYearField, cv.getYear());
				i.putExtra(DayAgenda.intentDateField, date);
				startActivity(i);
			}
			
		}
	};
*/	

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.slideview);

		View monthBarRight = findViewById(R.id.month_bar_right);
		monthBarRight.setOnClickListener(monthRight);

		View monthBarLeft = findViewById(R.id.month_bar_left);
		monthBarLeft.setOnClickListener(monthLeft);
		
		// Toggle Naming
	    CategoryCalDB db = ((CategoryCalendar) getApplication()).getDB();
	    List<Category> list = db.getAllCategories();
	    ToggleButton toggle1 = (ToggleButton) (findViewById(R.id.togglebutton1));
	    ToggleButton toggle2 = (ToggleButton) (findViewById(R.id.togglebutton2));
	    ToggleButton toggle3 = (ToggleButton) (findViewById(R.id.togglebutton3));
	    ToggleButton toggle4 = (ToggleButton) (findViewById(R.id.togglebutton4));
	    ToggleButton toggle5 = (ToggleButton) (findViewById(R.id.togglebutton5));
	    ToggleButton toggle6 = (ToggleButton) (findViewById(R.id.togglebutton6));
	    ToggleButton toggle7 = (ToggleButton) (findViewById(R.id.togglebutton7));
	    ToggleButton toggle8 = (ToggleButton) (findViewById(R.id.togglebutton8));
	    ToggleButton toggle9 = (ToggleButton) (findViewById(R.id.togglebutton9));
	    ToggleButton toggle10 = (ToggleButton) (findViewById(R.id.togglebutton10));
	    
	    // toggle 1 name
	    if (list.size()<1) {
	    	toggle1.setText("Empty");
	    	toggle1.setTextOn("Empty");
	    	toggle1.setTextOff("Empty");
	    }
	    else {
	    	Category category1 = list.get(0);
	    	toggle1.setText(category1.name);
	    	toggle1.setTextOn(category1.name);
	    	toggle1.setTextOff(category1.name + " off");
	    }
	    // toggle 2 name
	    if (list.size()<2) {
	    	toggle2.setText("Empty");
	    	toggle2.setTextOn("Empty");
	    	toggle2.setTextOff("Empty");
	    }
	    else {
	    	Category category2 = list.get(1);
	    	toggle2.setText(category2.name);
	    	toggle2.setTextOn(category2.name);
	    	toggle2.setTextOff(category2.name + " off");
	    }
	    // toggle 3 name
	    if (list.size()<3) {
	    	toggle3.setText("Empty");
	    	toggle3.setTextOn("Empty");
	    	toggle3.setTextOff("Empty");
	    }
	    else {
	    	Category category3 = list.get(2);
	    	toggle3.setText(category3.name);
	    	toggle3.setTextOn(category3.name);
	    	toggle3.setTextOff(category3.name + " off");
	    }
	    // toggle 4 name
	    if (list.size()<4) {
	    	toggle4.setText("Empty");
	    	toggle4.setTextOn("Empty");
	    	toggle4.setTextOff("Empty");
	    }
	    else {
	    	Category category4 = list.get(3);
	    	toggle4.setText(category4.name);
	    	toggle4.setTextOn(category4.name);
	    	toggle4.setTextOff(category4.name + " off");
	    }
	    // toggle 5 name
	    if (list.size()<5) {
	    	toggle5.setText("Empty");
	    	toggle5.setTextOn("Empty");
	    	toggle5.setTextOff("Empty");
	    }
	    else {
	    	Category category5 = list.get(4);
	    	toggle5.setText(category5.name);
	    	toggle5.setTextOn(category5.name);
	    	toggle5.setTextOff(category5.name + " off");
	    }
	    // toggle 6 name
	    if (list.size()<6) {
	    	toggle6.setText("Empty");
	    	toggle6.setTextOn("Empty");
	    	toggle6.setTextOff("Empty");
	    }
	    else {
	    	Category category6 = list.get(5);
	    	toggle6.setText(category6.name);
	    	toggle6.setTextOn(category6.name);
	    	toggle6.setTextOff(category6.name + " off");
	    }
	    // toggle 7 name
	    if (list.size()<7) {
	    	toggle7.setText("Empty");
	    	toggle7.setTextOn("Empty");
	    	toggle7.setTextOff("Empty");
	    }
	    else {
	    	Category category7 = list.get(6);
	    	toggle7.setText(category7.name);
	    	toggle7.setTextOn(category7.name);
	    	toggle7.setTextOff(category7.name + " off");
	    }
	    // toggle 8 name
	    if (list.size()<8) {
	    	toggle8.setText("Empty");
	    	toggle8.setTextOn("Empty");
	    	toggle8.setTextOff("Empty");
	    }
	    else {
	    	Category category8 = list.get(7);
	    	toggle8.setText(category8.name);
	    	toggle8.setTextOn(category8.name);
	    	toggle8.setTextOff(category8.name + " off");
	    }
	    // toggle 9 name
	    if (list.size()<9) {
	    	toggle9.setText("Empty");
	    	toggle9.setTextOn("Empty");
	    	toggle9.setTextOff("Empty");
	    }
	    else {
	    	Category category9 = list.get(8);
	    	toggle9.setText(category9.name);
	    	toggle9.setTextOn(category9.name);
	    	toggle9.setTextOff(category9.name + " off");
	    }
	    // toggle 10 name
	    if (list.size()<10) {
	    	toggle10.setText("Empty");
	    	toggle10.setTextOn("Empty");
	    	toggle10.setTextOff("Empty");
	    }
	    else {
	    	Category category10 = list.get(9);
	    	toggle10.setText(category10.name);
	    	toggle10.setTextOn(category10.name);
	    	toggle10.setTextOff(category10.name + " off");
	    }
		
		Intent i = getIntent();
		int loaderMonth = i.getIntExtra(intentMonthField, intentNoMonth);
		if (loaderMonth != intentNoMonth) {

			int loaderYear = i.getIntExtra(intentYearField, intentNoMonth);
			CalendarView cv = (CalendarView) findViewById(R.id.assign_indicators_calendar);
			cv.setCalendar(loaderMonth, loaderYear);
		}
		
/*		CalendarView cv = (CalendarView) findViewById(R.id.view_month_calendar);
		DateSquare dS = cv.ds;
		List<DayView> allDays = dS.getAllSquares();
		Iterator<DayView> iterDays = allDays.iterator();
		while (iterDays.hasNext()) {

			iterDays.next().setOnClickListener(DayPressListener);
		}
*/		
		// Check for version first run
		SharedPreferences sp = getPreferences(MODE_PRIVATE);
		if (sp.getFloat(lastRunVersion, 0) == 0) {

			// Store the latest run version
			SharedPreferences.Editor edit = sp.edit();
			edit.putFloat(lastRunVersion, (float) 1.1);
			edit.commit();

			Toast.makeText(
					this,
					"Welcome to your Agenda Calendar. Select \"Change Categories\" from the menu to add categories to the calendar.",
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.month_view_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent i = new Intent();
		ComponentName actName;

		switch (item.getItemId()) {
		case (R.id.menu_assign_indicators):
			actName = new ComponentName("com.DeezApps.Agenda_Calendar",
					"com.DeezApps.Agenda_Calendar.AssignIndicators");
			i.setComponent(actName);

			CalendarView cv = (CalendarView) findViewById(R.id.view_month_calendar);
			i.putExtra(AssignIndicators.intentMonthField, cv.getMonth());
			i.putExtra(AssignIndicators.intentYearField, cv.getYear());
			startActivity(i);
			break;
			
		case (R.id.menu_assign_indicators1):
			actName = new ComponentName("com.DeezApps.Agenda_Calendar",
					"com.DeezApps.Agenda_Calendar.AssignIndicators");
			i.setComponent(actName);

			CalendarView cv1 = (CalendarView) findViewById(R.id.view_month_calendar);
			i.putExtra(AssignIndicators.intentMonthField, cv1.getMonth());
			i.putExtra(AssignIndicators.intentYearField, cv1.getYear());
			startActivity(i);
			break;

		case (R.id.about):
			Toast.makeText(this, "This is your Agenda Calendar!", 
					Toast.LENGTH_LONG).show();
			Toast.makeText(this, "Categories are your agendas/deadlines to take care of for the day", 
					Toast.LENGTH_LONG).show();
			Toast.makeText(this, "Select \"Change Categories\" from the menu to add and assign categories to the calendar.", 
					Toast.LENGTH_LONG).show();
			Toast.makeText(this, "Slide the menu out from the left to turn categories on and off on the calendar", 
					Toast.LENGTH_LONG).show();
			break;	

		case (R.id.menu_modify_preferences):
			actName = new ComponentName("com.DeezApps.Agenda_Calendar",
					"com.DeezApps.Agenda_Calendar.ModifyPreferences");
			i.setComponent(actName);
			startActivity(i);
			break;
		}
		return true;
	}

	@Override
	public void onResume() {

		super.onResume();
		CalendarView cv = (CalendarView) findViewById(R.id.view_month_calendar);
		cv.redrawCalendar();

	}


	
	public void onToggleClicked(View view) {
	    // Is the toggle on?
	    boolean on = ((ToggleButton) view).isChecked();
	   
	    CalendarView cv = (CalendarView) (findViewById(R.id.view_month_calendar));
	    Toast.makeText(this, "All are " + (on ? "on" : "off"), Toast.LENGTH_LONG).show();
	    
	    ToggleButton toggle1 = (ToggleButton) (findViewById(R.id.togglebutton1));
	    ToggleButton toggle2 = (ToggleButton) (findViewById(R.id.togglebutton2));
	    ToggleButton toggle3 = (ToggleButton) (findViewById(R.id.togglebutton3));
	    ToggleButton toggle4 = (ToggleButton) (findViewById(R.id.togglebutton4));
	    ToggleButton toggle5 = (ToggleButton) (findViewById(R.id.togglebutton5));
	    ToggleButton toggle6 = (ToggleButton) (findViewById(R.id.togglebutton6));
	    ToggleButton toggle7 = (ToggleButton) (findViewById(R.id.togglebutton7));
	    ToggleButton toggle8 = (ToggleButton) (findViewById(R.id.togglebutton8));
	    ToggleButton toggle9 = (ToggleButton) (findViewById(R.id.togglebutton9));
	    ToggleButton toggle10 = (ToggleButton) (findViewById(R.id.togglebutton10));
   	 
	    if (on) {
	    	on1 = true; on2 = true; on3 = true; on4 = true; on5 = true;
	    	on6 = true; on7 = true; on8 = true; on9 = true; on10 = true;
	    	toggle1.setChecked(true);	toggle2.setChecked(true);
	    	toggle3.setChecked(true);	toggle4.setChecked(true);
	    	toggle5.setChecked(true);	toggle6.setChecked(true);
	    	toggle7.setChecked(true);	toggle8.setChecked(true);
	    	toggle9.setChecked(true);	toggle10.setChecked(true);
	    	cv.redrawCalendar();
	    }
	    else {
	    	on1 = false; on2 = false; on3 = false; on4 = false; on5 = false;
	    	on6 = false; on7 = false; on8 = false; on9 = false; on10 = false;
	    	toggle1.setChecked(false);	toggle2.setChecked(false);
	    	toggle3.setChecked(false);	toggle4.setChecked(false);
	    	toggle5.setChecked(false);	toggle6.setChecked(false);
	    	toggle7.setChecked(false);	toggle8.setChecked(false);
	    	toggle9.setChecked(false);	toggle10.setChecked(false);
	    	cv.redrawCalendar();
	    }
	}	
	
	public void onToggleClicked1(View view) {
	    // Is the toggle on?
	    on1 = ((ToggleButton) view).isChecked();
	   
	    CategoryCalDB db = ((CategoryCalendar) getApplication()).getDB();
	    List<Category> list = db.getAllCategories();
	    
	    // If toggle is not empty...
	    if (!(list.size()<1)) {
	    	Category category1 = list.get(0);
	    	Toast.makeText(this, category1.name + " switch is " + (on1 ? "on" : "off"),
	    			Toast.LENGTH_LONG).show();
	    	CalendarView cv = (CalendarView) (findViewById(R.id.view_month_calendar));
	    	cv.redrawCalendar();
	    }
	}
	
	
	public void onToggleClicked2(View view) {
	    // Is the toggle on?
		on2 = ((ToggleButton) view).isChecked();
		   
	    CategoryCalDB db = ((CategoryCalendar) getApplication()).getDB();
	    List<Category> list = db.getAllCategories();
	    
	    // If toggle is not empty...
	    if (!(list.size()<2)) {
	    	Category category2 = list.get(1);
	    	Toast.makeText(this, category2.name + " switch is " + (on2 ? "on" : "off"),
	    			Toast.LENGTH_LONG).show();
	    	CalendarView cv = (CalendarView) (findViewById(R.id.view_month_calendar));
	    	cv.redrawCalendar();
	    }
	}	
	
	
	public void onToggleClicked3(View view) {
	    // Is the toggle on?
		on3 = ((ToggleButton) view).isChecked();
		   
	    CategoryCalDB db = ((CategoryCalendar) getApplication()).getDB();
	    List<Category> list = db.getAllCategories();
	    
	    // If toggle is not empty...
	    if (!(list.size()<3)) {
	    	Category category3 = list.get(2);
	    	Toast.makeText(this, category3.name + " switch is " + (on3 ? "on" : "off"),
	    			Toast.LENGTH_LONG).show();
	    	CalendarView cv = (CalendarView) (findViewById(R.id.view_month_calendar));
	    	cv.redrawCalendar();
	    }
	}	
	
	public void onToggleClicked4(View view) {
	    // Is the toggle on?
		on4 = ((ToggleButton) view).isChecked();
		   
	    CategoryCalDB db = ((CategoryCalendar) getApplication()).getDB();
	    List<Category> list = db.getAllCategories();
	    
	    // If toggle is not empty...
	    if (!(list.size()<4)) {
	    	Category category4 = list.get(3);
	    	Toast.makeText(this, category4.name + " switch is " + (on4 ? "on" : "off"),
	    			Toast.LENGTH_LONG).show();
	    	CalendarView cv = (CalendarView) (findViewById(R.id.view_month_calendar));
	    	cv.redrawCalendar();
	    }
	}
	
	public void onToggleClicked5(View view) {
	    // Is the toggle on?
		on5 = ((ToggleButton) view).isChecked();
		   
	    CategoryCalDB db = ((CategoryCalendar) getApplication()).getDB();
	    List<Category> list = db.getAllCategories();
	    
	    // If toggle is not empty...
	    if (!(list.size()<5)) {
	    	Category category5 = list.get(4);
	    	Toast.makeText(this, category5.name + " switch is " + (on5 ? "on" : "off"),
	    			Toast.LENGTH_LONG).show();
	    	CalendarView cv = (CalendarView) (findViewById(R.id.view_month_calendar));
	    	cv.redrawCalendar();
	    }
	}
	
	public void onToggleClicked6(View view) {
	    // Is the toggle on?
		on6 = ((ToggleButton) view).isChecked();
		   
	    CategoryCalDB db = ((CategoryCalendar) getApplication()).getDB();
	    List<Category> list = db.getAllCategories();
	    
	    // If toggle is not empty...
	    if (!(list.size()<6)) {
	    	Category category6 = list.get(5);
	    	Toast.makeText(this, category6.name + " switch is " + (on6 ? "on" : "off"),
	    			Toast.LENGTH_LONG).show();
	    	CalendarView cv = (CalendarView) (findViewById(R.id.view_month_calendar));
	    	cv.redrawCalendar();
	    }
	}
	
	public void onToggleClicked7(View view) {
	    // Is the toggle on?
		on7 = ((ToggleButton) view).isChecked();
		   
	    CategoryCalDB db = ((CategoryCalendar) getApplication()).getDB();
	    List<Category> list = db.getAllCategories();
	    
	    // If toggle is not empty...
	    if (!(list.size()<7)) {
	    	Category category7 = list.get(6);
	    	Toast.makeText(this, category7.name + " switch is " + (on7 ? "on" : "off"),
	    			Toast.LENGTH_LONG).show();
	    	CalendarView cv = (CalendarView) (findViewById(R.id.view_month_calendar));
	    	cv.redrawCalendar();
	    }
	}
	
	public void onToggleClicked8(View view) {
	    // Is the toggle on?
		on8 = ((ToggleButton) view).isChecked();
		   
	    CategoryCalDB db = ((CategoryCalendar) getApplication()).getDB();
	    List<Category> list = db.getAllCategories();
	    
	    // If toggle is not empty...
	    if (!(list.size()<8)) {
	    	Category category8 = list.get(7);
	    	Toast.makeText(this, category8.name + " switch is " + (on8 ? "on" : "off"),
	    			Toast.LENGTH_LONG).show();
	    	CalendarView cv = (CalendarView) (findViewById(R.id.view_month_calendar));
	    	cv.redrawCalendar();
	    }
	}
	
	public void onToggleClicked9(View view) {
	    // Is the toggle on?
		on9 = ((ToggleButton) view).isChecked();
		   
	    CategoryCalDB db = ((CategoryCalendar) getApplication()).getDB();
	    List<Category> list = db.getAllCategories();
	    
	    // If toggle is not empty...
	    if (!(list.size()<9)) {
	    	Category category9 = list.get(8);
	    	Toast.makeText(this, category9.name + " switch is " + (on9 ? "on" : "off"),
	    			Toast.LENGTH_LONG).show();
	    	CalendarView cv = (CalendarView) (findViewById(R.id.view_month_calendar));
	    	cv.redrawCalendar();
	    }
	}
	
	public void onToggleClicked10(View view) {
	    // Is the toggle on?
		on10 = ((ToggleButton) view).isChecked();
		   
	    CategoryCalDB db = ((CategoryCalendar) getApplication()).getDB();
	    List<Category> list = db.getAllCategories();
	    
	    // If toggle is not empty...
	    if (!(list.size()<10)) {
	    	Category category10 = list.get(9);
	    	Toast.makeText(this, category10.name + " switch is " + (on10 ? "on" : "off"),
	    			Toast.LENGTH_LONG).show();
	    	CalendarView cv = (CalendarView) (findViewById(R.id.view_month_calendar));
	    	cv.redrawCalendar();
	    }
	}
	
}