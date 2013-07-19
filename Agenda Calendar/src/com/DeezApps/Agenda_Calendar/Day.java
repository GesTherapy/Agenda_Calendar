package com.DeezApps.Agenda_Calendar;
/**
 * * based on open source github library from
 * @author Matthew Moss
 *
 ** further developed for Agenda Calendar
 */
import java.util.Date;

public class Day {

	Date date;
	int category1Id, category2Id, category3Id, category4Id;
	String category1Symbol, category2Symbol, category3Symbol, category4Symbol;
	int category1Color, category2Color, category3Color, category4Color;

	public Day(int year, int month, int day, int category1id, int category2id, int category3id, int category4id) {

		date = new Date();
		date.setYear(year - 1900);
		date.setMonth(month);
		date.setDate(day);
		this.category1Id = category1id;
		this.category2Id = category2id;
		this.category3Id = category3id;
		this.category4Id = category4id;
		
	}

	public Day() {

		date = new Date();
		
	}
	
}