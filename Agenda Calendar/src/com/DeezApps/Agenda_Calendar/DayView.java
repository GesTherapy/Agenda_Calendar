package com.DeezApps.Agenda_Calendar;
/**
 * * based on open source github library from
 * @author Matthew Moss
 *
 ** further developed for Agenda Calendar
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DayView extends RelativeLayout {

	public static final int NO_DATE = -1;
	public static final int NO_CATEGORY = -1;

	private int date = NO_DATE;

	public int category1Id = NO_CATEGORY;
	public int category2Id = NO_CATEGORY;
	public int category3Id = NO_CATEGORY;
	public int category4Id = NO_CATEGORY;

	public DayView(Context context) {

		super(context);
		LayoutInflater.from(getContext())
				.inflate(R.layout.day_view, this, true);
	}

	public DayView(Context context, AttributeSet attrs) {

		super(context, attrs);
		LayoutInflater.from(getContext())
				.inflate(R.layout.day_view, this, true);
	}

	public void setDate(int newDate) {

		TextView t = (TextView) this.findViewById(R.id.day_view_date);
		date = newDate;

		if (date != NO_DATE) {
			t.setText(String.valueOf(newDate));
		} else {
			t.setText("");
		}
	}

	public void setDateColor(int newColor) {

		TextView t = (TextView) this.findViewById(R.id.day_view_date);
		t.setTextColor(newColor);
	}

	public void setLabel1Text(String newtext) {

		TextView t = (TextView) this.findViewById(R.id.day_view_label1);
		t.setText(newtext);
	}
	
	public void setLabel2Text(String newtext) {

		TextView t = (TextView) this.findViewById(R.id.day_view_label2);
		t.setText(newtext);
	}
	
	public void setLabel3Text(String newtext) {

		TextView t = (TextView) this.findViewById(R.id.day_view_label3);
		t.setText(newtext);
	}
	
	public void setLabel4Text(String newtext) {

		TextView t = (TextView) this.findViewById(R.id.day_view_label4);
		t.setText(newtext);
	}

	public void setLabel1Color(int newColor) {

		TextView t = (TextView) this.findViewById(R.id.day_view_label1);
		t.setTextColor(newColor);
	}
	
	public void setLabel2Color(int newColor) {

		TextView t = (TextView) this.findViewById(R.id.day_view_label2);
		t.setTextColor(newColor);
	}
	
	public void setLabel3Color(int newColor) {

		TextView t = (TextView) this.findViewById(R.id.day_view_label3);
		t.setTextColor(newColor);
	}
	
	public void setLabel4Color(int newColor) {

		TextView t = (TextView) this.findViewById(R.id.day_view_label4);
		t.setTextColor(newColor);
	}

	public int getDate() {

		return date;
	}
}
