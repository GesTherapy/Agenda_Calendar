package com.DeezApps.Agenda_Calendar;
/**
 * * based on open source github library from
 * @author Matthew Moss
 *
 ** further developed for Agenda Calendar
 */
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalendarView extends RelativeLayout {

	static final int Monday = 0;
	static final int Tuesday = 1;
	static final int Wednesday = 2;
	static final int Thursday = 3;
	static final int Friday = 4;
	static final int Saturday = 5;
	static final int Sunday = 6;

	int month;
	int year;

	int weekStartDay;

	DateSquare ds;
	CategoryCalDB db;

	TextView dayNames[] = new TextView[7];

	public CalendarView(Context context, AttributeSet attrs) {

		super(context, attrs);
		loadCalendar(context);
	}

	public void loadCalendar(Context context) {

		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);
		this.weekStartDay = Integer.parseInt(pref.getString(
				context.getString(R.string.pref_first_day_of_week), "2"));

		View.inflate(context, R.layout.calendar_view, this);

		Calendar cal = Calendar.getInstance();
		this.ds = (DateSquare) findViewById(R.id.DateSquare);
		this.db = ((CategoryCalendar) context.getApplicationContext()).getDB();

		LinearLayout dayNameBar = (LinearLayout) findViewById(R.id.day_bar);

		for (int i = 0; i < 7; i++) {

			this.dayNames[i] = (TextView) inflate(context,
					R.layout.calendar_view_day_name, null);

			dayNameBar.addView(dayNames[i]);

			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dayNames[i]
					.getLayoutParams();
			params.weight = 1;
			params.width = LayoutParams.FILL_PARENT;
			dayNames[i].setLayoutParams(params);

			dayNames[i].setGravity(Gravity.CENTER);
		}

		this.month = cal.get(Calendar.MONTH);
		this.year = cal.get(Calendar.YEAR);
	}

	private int getFirstXCoord(int year, int month) {

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);
		int firstDay = cal.get(Calendar.DAY_OF_WEEK);

		int monCoord = 0;

		switch (firstDay) {

		case Calendar.MONDAY:
			monCoord = 0;
			break;
		case Calendar.TUESDAY:
			monCoord = 1;
			break;
		case Calendar.WEDNESDAY:
			monCoord = 2;
			break;
		case Calendar.THURSDAY:
			monCoord = 3;
			break;
		case Calendar.FRIDAY:
			monCoord = 4;
			break;
		case Calendar.SATURDAY:
			monCoord = 5;
			break;
		case Calendar.SUNDAY:
			monCoord = 6;
			break;
		}

		return ((monCoord - weekStartDay) + (weekStartDay * 7)) % 7;
	}

	private DayView getDayView(Date d) {

		int firstXCoord = getFirstXCoord(d.getYear() + 1900, d.getMonth());
		int gap = d.getDate() - 1;

		int length = firstXCoord + gap;

		int xCoord = length % 7;
		int yCoord = (int) Math.floor((double) length / 7);

		return (DayView) ds.getSquareByPos(xCoord, yCoord);
	}

	public void setCalendar(int month, int year) {

		this.month = month;
		this.year = year;
		// Calendar redrawing removed here to allow later optimisation

	}

	public void increaseMonth() {

		month++;

		if (month > 11) {

			month = 0;
			year++;
		}

		this.redrawCalendar();
	}

	public void decreaseMonth() {

		month--;

		if (month < 0) {

			month = 11;
			year--;
		}

		this.redrawCalendar();
	}

	public int getMonth() {

		return this.month;
	}

	public int getYear() {

		return this.year;
	}

	public void setMonth(int newMonth) {

		this.setCalendar(newMonth, this.year);
	}

	public void setYear(int newYear) {

		this.setCalendar(this.month, newYear);
	}

	public void setWeekStart(int day) {
		// Monday = 0, Tuesday = 1 etc...
		this.weekStartDay = day;
		redrawCalendar();
	}
	
	
	public void redrawCalendar() {

		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(getContext());

		this.weekStartDay = Integer.parseInt(pref.getString(getContext()
				.getString(R.string.pref_first_day_of_week), "0"));

		// Here's the magic
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);

		Calendar currentDate = Calendar.getInstance();

		// Find the square of the first day of the month by day
		int XCoord = getFirstXCoord(year, month);

		// Set the next n squares to ascending numbers

		int totalDays = cal.getActualMaximum(Calendar.DATE);
		int daysDrawn = 0;

		for (int y = 0; y < 6; y++) {

			for (int x = 0; x < 7; x++) {

				DayView currentDay = ds.getSquareByPos(x, y);
				currentDay.category1Id = DayView.NO_CATEGORY;
				currentDay.category2Id = DayView.NO_CATEGORY;
				currentDay.category3Id = DayView.NO_CATEGORY;
				currentDay.category4Id = DayView.NO_CATEGORY;

				MarginLayoutParams dayMargins = (MarginLayoutParams) currentDay
						.getLayoutParams();

				if (y == 0 && x < XCoord) {
					currentDay.setDate(DayView.NO_DATE);
					currentDay.setLabel1Text("");
					currentDay.setLabel2Text("");
					currentDay.setLabel3Text("");
					currentDay.setLabel4Text("");
					dayMargins.setMargins(0, 0, 0, 1);

					if (x == XCoord - 1) {

						dayMargins.rightMargin++;
					}

				} else if (daysDrawn < totalDays) {
					daysDrawn++;
					currentDay.setDate(daysDrawn);
					currentDay.setLabel1Text("");
					currentDay.setLabel2Text("");
					currentDay.setLabel3Text("");
					currentDay.setLabel4Text("");
					dayMargins.setMargins(1, 1, 1, 1);

					if (y == 0) {
						dayMargins.topMargin++;
					}
					if (x == 6) {
						dayMargins.rightMargin++;
					}
					if (x == 0) {
						dayMargins.leftMargin++;
					}
					if (y == 5) {
						dayMargins.bottomMargin++;
					}

					// Highlight the current day
					if (currentDate.get(Calendar.YEAR) == year
							&& currentDate.get(Calendar.MONTH) == month
							&& currentDate.get(Calendar.DAY_OF_MONTH) == daysDrawn) {

						currentDay.setDateColor(0xFFFFFFFF);
					} else {
						currentDay.setDateColor(Color.DKGRAY);
					}

				} else {
					currentDay.setDate(DayView.NO_DATE);
					currentDay.setLabel1Text("");
					currentDay.setLabel2Text("");
					currentDay.setLabel3Text("");
					currentDay.setLabel4Text("");
					dayMargins.setMargins(0, 0, 0, 0);

					if (daysDrawn == totalDays) {

						dayMargins.leftMargin++;

					}

					if (daysDrawn < totalDays + 7) {

						dayMargins.topMargin++;

					}

					daysDrawn++;
				}

				currentDay.setLayoutParams(dayMargins);

			}
		}

		// Update the calendar month bar

		TextView monthBar = (TextView) findViewById(R.id.month_bar);

		int currentMonth = (cal.get(Calendar.MONTH));

		String currentMonthName = "Borken";

		switch (currentMonth) {

		case Calendar.JANUARY:
			currentMonthName = "January";
			break;
		case Calendar.FEBRUARY:
			currentMonthName = "February";
			break;
		case Calendar.MARCH:
			currentMonthName = "March";
			break;
		case Calendar.APRIL:
			currentMonthName = "April";
			break;
		case Calendar.MAY:
			currentMonthName = "May";
			break;
		case Calendar.JUNE:
			currentMonthName = "June";
			break;
		case Calendar.JULY:
			currentMonthName = "July";
			break;
		case Calendar.AUGUST:
			currentMonthName = "August";
			break;
		case Calendar.SEPTEMBER:
			currentMonthName = "September";
			break;
		case Calendar.OCTOBER:
			currentMonthName = "October";
			break;
		case Calendar.NOVEMBER:
			currentMonthName = "November";
			break;
		case Calendar.DECEMBER:
			currentMonthName = "December";
			break;
		}

		monthBar.setText(currentMonthName + " " + Integer.toString(year));

		Resources res = getResources();
		String[] dayTitles = res.getStringArray(R.array.short_day_names);
		// Draw the day name column tops
		for (int i = 0; i < 7; i++) {

			// Draw Location
			dayNames[i].setText(dayTitles[(i + weekStartDay) % 7]);

		}

		Date current = cal.getTime();

		List<Day> categories = db.getMonthCategories(current);
		Iterator<Day> d = categories.iterator();

		while (d.hasNext()) {

		    List<Category> list = db.getAllCategories();
		    
			Day draw = d.next();
			DayView dv = (DayView) getDayView(draw.date);
			
			if (list.size()>=1) {
				if (draw.category1Id==(list.get(0)).id) {
					if(ViewMonth.on1) {
						dv.setLabel1Text(draw.category1Symbol);
					} else {
						dv.setLabel1Text("");
					}
				}
				if (draw.category2Id==(list.get(0)).id) {
					if(ViewMonth.on1) {
						dv.setLabel2Text(draw.category2Symbol);
					} else {
						dv.setLabel2Text("");
					}
				}
				if (draw.category3Id==(list.get(0)).id) {
					if(ViewMonth.on1) {
						dv.setLabel3Text(draw.category3Symbol);
					} else {
						dv.setLabel3Text("");
					}
				}
				if (draw.category4Id==(list.get(0)).id) {
					if(ViewMonth.on1) {
						dv.setLabel4Text(draw.category4Symbol);
					} else {
						dv.setLabel4Text("");
					}
				}
			}
			
			if (list.size()>=2) {
				if (draw.category1Id==(list.get(1)).id) {
					if(ViewMonth.on2) {
						dv.setLabel1Text(draw.category1Symbol);
					} else {
						dv.setLabel1Text("");
					}
				}
				if (draw.category2Id==(list.get(1)).id) {
					if(ViewMonth.on2) {
						dv.setLabel2Text(draw.category2Symbol);
					} else {
						dv.setLabel2Text("");
					}
				}
				if (draw.category3Id==(list.get(1)).id) {
					if(ViewMonth.on2) {
						dv.setLabel3Text(draw.category3Symbol);
					} else {
						dv.setLabel3Text("");
					}
				}
				if (draw.category4Id==(list.get(1)).id) {
					if(ViewMonth.on2) {
						dv.setLabel4Text(draw.category4Symbol);
					} else {
						dv.setLabel4Text("");
					}
				}
			}
			
			if (list.size()>=3) {
				if (draw.category1Id==(list.get(2)).id) {
					if(ViewMonth.on3) {
						dv.setLabel1Text(draw.category1Symbol);
					} else {
						dv.setLabel1Text("");
					}
				}
				if (draw.category2Id==(list.get(2)).id) {
					if(ViewMonth.on3) {
						dv.setLabel2Text(draw.category2Symbol);
					} else {
						dv.setLabel2Text("");
					}
				}
				if (draw.category3Id==(list.get(2)).id) {
					if(ViewMonth.on3) {
						dv.setLabel3Text(draw.category3Symbol);
					} else {
						dv.setLabel3Text("");
					}
				}
				if (draw.category4Id==(list.get(2)).id) {
					if(ViewMonth.on3) {
						dv.setLabel4Text(draw.category4Symbol);
					} else {
						dv.setLabel4Text("");
					}
				}
			}
			
			if (list.size()>=4) {
				if (draw.category1Id==(list.get(3)).id) {
					if(ViewMonth.on4) {
						dv.setLabel1Text(draw.category1Symbol);
					} else {
						dv.setLabel1Text("");
					}
				}
				if (draw.category2Id==(list.get(3)).id) {
					if(ViewMonth.on4) {
						dv.setLabel2Text(draw.category2Symbol);
					} else {
						dv.setLabel2Text("");
					}
				}
				if (draw.category3Id==(list.get(3)).id) {
					if(ViewMonth.on4) {
						dv.setLabel3Text(draw.category3Symbol);
					} else {
						dv.setLabel3Text("");
					}
				}
				if (draw.category4Id==(list.get(3)).id) {
					if(ViewMonth.on4) {
						dv.setLabel4Text(draw.category4Symbol);
					} else {
						dv.setLabel4Text("");
					}
				}
			}
			
			if (list.size()>=5) {
				if (draw.category1Id==(list.get(4)).id) {
					if(ViewMonth.on5) {
						dv.setLabel1Text(draw.category1Symbol);
					} else {
						dv.setLabel1Text("");
					}
				}
				if (draw.category2Id==(list.get(4)).id) {
					if(ViewMonth.on5) {
						dv.setLabel2Text(draw.category2Symbol);
					} else {
						dv.setLabel2Text("");
					}
				}
				if (draw.category3Id==(list.get(4)).id) {
					if(ViewMonth.on5) {
						dv.setLabel3Text(draw.category3Symbol);
					} else {
						dv.setLabel3Text("");
					}
				}
				if (draw.category4Id==(list.get(4)).id) {
					if(ViewMonth.on5) {
						dv.setLabel4Text(draw.category4Symbol);
					} else {
						dv.setLabel4Text("");
					}
				}
			}
			
			if (list.size()>=6) {
				if (draw.category1Id==(list.get(5)).id) {
					if(ViewMonth.on6) {
						dv.setLabel1Text(draw.category1Symbol);
					} else {
						dv.setLabel1Text("");
					}
				}
				if (draw.category2Id==(list.get(5)).id) {
					if(ViewMonth.on6) {
						dv.setLabel2Text(draw.category2Symbol);
					} else {
						dv.setLabel2Text("");
					}
				}
				if (draw.category3Id==(list.get(5)).id) {
					if(ViewMonth.on6) {
						dv.setLabel3Text(draw.category3Symbol);
					} else {
						dv.setLabel3Text("");
					}
				}
				if (draw.category4Id==(list.get(5)).id) {
					if(ViewMonth.on6) {
						dv.setLabel4Text(draw.category4Symbol);
					} else {
						dv.setLabel4Text("");
					}
				}
			}
			
			if (list.size()>=7) {
				if (draw.category1Id==(list.get(6)).id) {
					if(ViewMonth.on7) {
						dv.setLabel1Text(draw.category1Symbol);
					} else {
						dv.setLabel1Text("");
					}
				}
				if (draw.category2Id==(list.get(6)).id) {
					if(ViewMonth.on7) {
						dv.setLabel2Text(draw.category2Symbol);
					} else {
						dv.setLabel2Text("");
					}
				}
				if (draw.category3Id==(list.get(6)).id) {
					if(ViewMonth.on7) {
						dv.setLabel3Text(draw.category3Symbol);
					} else {
						dv.setLabel3Text("");
					}
				}
				if (draw.category4Id==(list.get(6)).id) {
					if(ViewMonth.on7) {
						dv.setLabel4Text(draw.category4Symbol);
					} else {
						dv.setLabel4Text("");
					}
				}
			}
			
			if (list.size()>=8) {
				if (draw.category1Id==(list.get(7)).id) {
					if(ViewMonth.on8) {
						dv.setLabel1Text(draw.category1Symbol);
					} else {
						dv.setLabel1Text("");
					}
				}
				if (draw.category2Id==(list.get(7)).id) {
					if(ViewMonth.on8) {
						dv.setLabel2Text(draw.category2Symbol);
					} else {
						dv.setLabel2Text("");
					}
				}
				if (draw.category3Id==(list.get(7)).id) {
					if(ViewMonth.on8) {
						dv.setLabel3Text(draw.category3Symbol);
					} else {
						dv.setLabel3Text("");
					}
				}
				if (draw.category4Id==(list.get(7)).id) {
					if(ViewMonth.on8) {
						dv.setLabel4Text(draw.category4Symbol);
					} else {
						dv.setLabel4Text("");
					}
				}
			}
			
			if (list.size()>=9) {
				if (draw.category1Id==(list.get(8)).id) {
					if(ViewMonth.on9) {
						dv.setLabel1Text(draw.category1Symbol);
					} else {
						dv.setLabel1Text("");
					}
				}
				if (draw.category2Id==(list.get(8)).id) {
					if(ViewMonth.on9) {
						dv.setLabel2Text(draw.category2Symbol);
					} else {
						dv.setLabel2Text("");
					}
				}
				if (draw.category3Id==(list.get(8)).id) {
					if(ViewMonth.on9) {
						dv.setLabel3Text(draw.category3Symbol);
					} else {
						dv.setLabel3Text("");
					}
				}
				if (draw.category4Id==(list.get(8)).id) {
					if(ViewMonth.on9) {
						dv.setLabel4Text(draw.category4Symbol);
					} else {
						dv.setLabel4Text("");
					}
				}
			}
			
			if (list.size()>=10) {
				if (draw.category1Id==(list.get(9)).id) {
					if(ViewMonth.on10) {
						dv.setLabel1Text(draw.category1Symbol);
					} else {
						dv.setLabel1Text("");
					}
				}
				if (draw.category2Id==(list.get(9)).id) {
					if(ViewMonth.on10) {
						dv.setLabel2Text(draw.category2Symbol);
					} else {
						dv.setLabel2Text("");
					}
				}
				if (draw.category3Id==(list.get(9)).id) {
					if(ViewMonth.on10) {
						dv.setLabel3Text(draw.category3Symbol);
					} else {
						dv.setLabel3Text("");
					}
				}
				if (draw.category4Id==(list.get(9)).id) {
					if(ViewMonth.on10) {
						dv.setLabel4Text(draw.category4Symbol);
					} else {
						dv.setLabel4Text("");
					}
				}
			}

			dv.setLabel1Color(draw.category1Color);
			dv.setLabel2Color(draw.category2Color);
			dv.setLabel3Color(draw.category3Color);
			dv.setLabel4Color(draw.category4Color);
			dv.category1Id = draw.category1Id;
			dv.category2Id = draw.category2Id;
			dv.category3Id = draw.category3Id;
			dv.category4Id = draw.category4Id;
		}
	}
}