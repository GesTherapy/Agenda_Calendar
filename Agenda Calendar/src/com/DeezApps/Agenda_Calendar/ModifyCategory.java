package com.DeezApps.Agenda_Calendar;
/**
 * * based on open source github library from
 * @author Matthew Moss
 *
 ** further developed for Agenda Calendar
 */
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ModifyCategory extends Activity {

	private CategoryCalDB dbConnect = null;

	private OnClickListener save = new OnClickListener() {

		public void onClick(View v) {

			TextView nameField = (TextView) findViewById(R.id.modify_category_name);
			TextView symbolField = (TextView) findViewById(R.id.modify_category_symbol);
			TextView commentField = (TextView) findViewById(R.id.modify_category_comment);

			if (nameField.getText().toString().length() > 0) {

				Category toAdd = new Category();
				toAdd.name = nameField.getText().toString();
				toAdd.symbol = symbolField.getText().toString();
				toAdd.comments = commentField.getText().toString();

				// Get the color displayed by the spinner
				Spinner colorChooser = (Spinner) findViewById(R.id.modify_category_choose_color);
				String colorAsString = (String) colorChooser.getSelectedItem();

				if ("Red".equals(colorAsString)) {
					toAdd.color = Color.RED;
				} else if ("Yellow".equals(colorAsString)) {
					toAdd.color = Color.YELLOW;
				} else if ("Green".equals(colorAsString)) {
					toAdd.color = Color.GREEN;
				} else if ("Blue".equals(colorAsString)) {
					toAdd.color = Color.BLUE;
				} else if ("Purple".equals(colorAsString)) {
					toAdd.color = Color.MAGENTA;
				} else if ("Gray".equals(colorAsString)) {
					toAdd.color = 0xFFCCCCCC;
				} else if ("Cyan".equals(colorAsString)) {
					toAdd.color = Color.CYAN;
				} else if ("Orange".equals(colorAsString)) {
					toAdd.color = 0xFFFF9900;
				} else if ("Pink".equals(colorAsString)) {
					toAdd.color = 0xFFFF0099;
				} else {
					toAdd.color = Color.WHITE;
				}

				if (dbConnect == null) {
					dbConnect = ((CategoryCalendar) getApplication()).getDB();
				}

				// If there's an old version, delete it.

				Intent i = getIntent();

				if (i.hasExtra("Modify")) {

					dbConnect.updateCategory(i.getIntExtra("Modify", 0), toAdd);
				} else {

					dbConnect.insertCategory(toAdd);

				}

				finish();

			} else {

				CharSequence helpText = "Please set a name for this category";
				Toast popup = Toast.makeText(getApplicationContext(), helpText,
						Toast.LENGTH_SHORT);
				popup.show();

			}

		}
	};

	private OnClickListener cancel = new OnClickListener() {

		public void onClick(View v) {

			finish();

		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_category);

		// Save button event listener
		Button saveButton = (Button) findViewById(R.id.modify_category_save);
		saveButton.setOnClickListener(save);

		// Cancel button event listener
		Button cancelButton = (Button) findViewById(R.id.modify_category_cancel);
		cancelButton.setOnClickListener(cancel);

		// Bind the color spinner
		Spinner colorChooser = (Spinner) findViewById(R.id.modify_category_choose_color);
		ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter
				.createFromResource(this, R.array.colors_array,
						android.R.layout.simple_spinner_item);
		colorAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		colorChooser.setAdapter(colorAdapter);


		// Here's the fun part. Check for preexisting category
		Intent i = getIntent();
		if (i.hasExtra("Modify")) {

			int oldId = i.getIntExtra("Modify", 0);
			dbConnect = ((CategoryCalendar) getApplication()).getDB();
			Category sh = dbConnect.getCategoryByID(oldId);

			EditText nameField = (EditText) findViewById(R.id.modify_category_name);
			nameField.setText(sh.name);

			EditText symbolField = (EditText) findViewById(R.id.modify_category_symbol);
			symbolField.setText(sh.name);

			if (sh.color == Color.RED) {
				colorChooser.setSelection(colorAdapter.getPosition("Red"));

			} else if (sh.color == Color.YELLOW) {
				colorChooser.setSelection(colorAdapter.getPosition("Yellow"));

			} else if (sh.color == Color.GREEN) {
				colorChooser.setSelection(colorAdapter.getPosition("Green"));

			} else if (sh.color == Color.BLUE) {
				colorChooser.setSelection(colorAdapter.getPosition("Blue"));

			} else if (sh.color == Color.MAGENTA) {
				colorChooser.setSelection(colorAdapter.getPosition("Purple"));
			}

			EditText commentField = (EditText) findViewById(R.id.modify_category_comment);
			commentField.setText(sh.comments);

		}

	}

}