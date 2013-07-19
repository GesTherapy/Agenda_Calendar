package com.DeezApps.Agenda_Calendar;
/**
 * * based on open source github library from
 * @author Matthew Moss
 *
 ** further developed for Agenda Calendar
 */
import java.util.Date;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AssignIndicators extends Activity {

	CategoryCalDB db;

	private static final String lastRunVersion = "Version";

	static final String intentMonthField = "launchMonth";
	static final String intentYearField = "launchYear";
	static final int intentNoMonth = -1;
	
	Category selectedCategory;

	private OnClickListener monthRight = new OnClickListener() {

		public void onClick(View v) {

			CalendarView cv = (CalendarView) findViewById(R.id.assign_indicators_calendar);
			cv.increaseMonth();
		}
	};

	private OnClickListener monthLeft = new OnClickListener() {

		public void onClick(View v) {

			CalendarView cv = (CalendarView) findViewById(R.id.assign_indicators_calendar);
			cv.decreaseMonth();
		}
	};

	private OnClickListener DayPressListener = new OnClickListener() {

		public void onClick(View v) {

			// There must be a better way
			DayView dV = (DayView) v;

			// If the day is legit ie. actually a square
			int date = dV.getDate();
			if (date != DayView.NO_DATE) {

				if (selectedCategory != null) {
					
					if (selectedCategory.id!=dV.category1Id && selectedCategory.id!=dV.category2Id && 
						selectedCategory.id!=dV.category3Id && selectedCategory.id!=dV.category4Id) {
						// A new type of category is selected, update UI
						// If there is an empty spot, put the category in there
						// Else all slots are full, so just leave as is
						if (dV.category1Id==DayView.NO_CATEGORY) {
							dV.setLabel1Text(selectedCategory.symbol);
							dV.setLabel1Color(selectedCategory.color);
							dV.category1Id = selectedCategory.id;
						}
						else if (dV.category2Id==DayView.NO_CATEGORY) {
							dV.setLabel2Text(selectedCategory.symbol);
							dV.setLabel2Color(selectedCategory.color);
							dV.category2Id = selectedCategory.id;
						}
						else if (dV.category3Id==DayView.NO_CATEGORY) {
							dV.setLabel3Text(selectedCategory.symbol);
							dV.setLabel3Color(selectedCategory.color);
							dV.category3Id = selectedCategory.id;
						}
						else if (dV.category4Id==DayView.NO_CATEGORY) {
							dV.setLabel4Text(selectedCategory.symbol);
							dV.setLabel4Color(selectedCategory.color);
							dV.category4Id = selectedCategory.id;
						}
					}
					else if (selectedCategory.id==dV.category1Id) {
						// Same category is selected. Don't want indicator to be seen. Clear square
						dV.setLabel1Text("");
						dV.category1Id = DayView.NO_CATEGORY;
					}
					else if (selectedCategory.id==dV.category2Id) {
						// Same category is selected. Don't want indicator to be seen. Clear square
						dV.setLabel2Text("");
						dV.category2Id = DayView.NO_CATEGORY;
					}
					else if (selectedCategory.id==dV.category3Id) {
						// Same category is selected. Don't want indicator to be seen. Clear square
						dV.setLabel3Text("");
						dV.category3Id = DayView.NO_CATEGORY;
					}
					else if (selectedCategory.id==dV.category4Id) {
						// Same category is selected. Don't want indicator to be seen. Clear square
						dV.setLabel4Text("");
						dV.category4Id = DayView.NO_CATEGORY;
					}

					// Here's where the ASYNC will be, eventually.

					CalendarView cv = (CalendarView) findViewById(R.id.assign_indicators_calendar);

					CategoryCalDB db = ((CategoryCalendar) getApplication()).getDB();
					
					Date focus = new Date();
					focus.setYear(cv.year - 1900);
					focus.setMonth(cv.month);
					focus.setDate(date);
					
					// Set up a new Day with the adjusted categories
					Day newDay = new Day(cv.year, cv.month, date, 
							dV.category1Id, dV.category2Id, dV.category3Id, dV.category4Id);
					
					// replace Day in database with new one
					db.clearDay(focus);
					db.setDayCategory(newDay);

				}
				else {

					Toast.makeText(
							getApplicationContext(),
							"You havn't set any category types yet. Try \"Edit Category Types\" in the menu.",
							Toast.LENGTH_LONG).show();
				}
			}
		}
	};

	private class CategorySelectionListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			TextView symbolSpot = (TextView) findViewById(R.id.assign_indicators_symbol);

			Category selected = (Category) arg0.getItemAtPosition(arg2);

			symbolSpot.setText(selected.symbol);
			symbolSpot.setTextColor(selected.color);
			symbolSpot.setVisibility(View.VISIBLE);

			selectedCategory = selected;
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// Chill, bro

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.assign_indicators);

		db = ((CategoryCalendar) getApplication()).getDB();

		View monthBarRight = findViewById(R.id.month_bar_right);
		monthBarRight.setOnClickListener(monthRight);

		View monthBarLeft = findViewById(R.id.month_bar_left);
		monthBarLeft.setOnClickListener(monthLeft);

		Spinner categorySelector = (Spinner) findViewById(R.id.assign_indicators_category_selector);
		categorySelector.setOnItemSelectedListener(new CategorySelectionListener());

		CalendarView cv = (CalendarView) findViewById(R.id.assign_indicators_calendar);

		Intent i = getIntent();
		int loaderMonth = i.getIntExtra(intentMonthField, intentNoMonth);
		if (loaderMonth != intentNoMonth) {

			int loaderYear = i.getIntExtra(intentYearField, intentNoMonth);
			cv.setCalendar(loaderMonth, loaderYear);
		}

		DateSquare dS = cv.ds;
		List<DayView> allDays = dS.getAllSquares();
		Iterator<DayView> iterDays = allDays.iterator();
		while (iterDays.hasNext()) {

			iterDays.next().setOnClickListener(DayPressListener);
		}

		// Check for version first run
		SharedPreferences sp = getPreferences(MODE_PRIVATE);
		if (sp.getFloat(lastRunVersion, 0) == 0) {

			// Store the latest run version
			SharedPreferences.Editor edit = sp.edit();
			edit.putFloat(lastRunVersion, (float) 1.1);
			edit.commit();

			Toast.makeText(
					this,
					"Select \"Edit Category Types\" from the menu to add new or edit categories.",
					Toast.LENGTH_LONG).show();
			Toast.makeText(
					this,
					"Categories are your agendas/deadlines to take care of for the day",
					Toast.LENGTH_LONG).show();
			Toast.makeText(
					this,
					"Add categories and then select them below to click on dates to add/remove the agenda",
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public void onResume() {

		super.onResume();

		Spinner categorySelector = (Spinner) findViewById(R.id.assign_indicators_category_selector);
		List<Category> categories = db.getAllCategories();
		ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				categories);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		categorySelector.setAdapter(adapter);
		CalendarView cv = (CalendarView) findViewById(R.id.assign_indicators_calendar);
		cv.redrawCalendar();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.assign_indicators_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent i = new Intent();
		ComponentName actName;

		switch (item.getItemId()) {
		case (R.id.menu_edit_categories):
			actName = new ComponentName("com.DeezApps.Agenda_Calendar",
					"com.DeezApps.Agenda_Calendar.ManageCategories");
			i.setComponent(actName);
			startActivity(i);
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
}