package com.DeezApps.Agenda_Calendar;
/**
 * * based on open source github library from
 * @author Matthew Moss
 *
 ** further developed for Agenda Calendar
 */
import android.app.Application;

public class CategoryCalendar extends Application {

	private CategoryCalDB db = null;

	public CategoryCalDB getDB() {

		if (db == null) {
			db = new CategoryCalDB(getApplicationContext());
		}
		return db;
	}

}