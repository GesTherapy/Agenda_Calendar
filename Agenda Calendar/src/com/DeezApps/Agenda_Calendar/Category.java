package com.DeezApps.Agenda_Calendar;
/**
 * * based on open source github library from
 * @author Matthew Moss
 *
 ** further developed for Agenda Calendar
 */

public class Category {

	public int id;

	public String name;
	public String symbol;
	public int color = 0xFFFFFFFF;

	public String comments;

	public Category() {

	}

	@Override
	public String toString() {

		return this.name;
	}
}