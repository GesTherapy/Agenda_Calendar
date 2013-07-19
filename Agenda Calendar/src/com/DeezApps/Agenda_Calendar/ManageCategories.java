package com.DeezApps.Agenda_Calendar;
/**
 * * based on open source github library from
 * @author Matthew Moss
 *
 ** further developed for Agenda Calendar
 */

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ManageCategories extends Activity {

	CategoryListAdapter listContent;

	private OnClickListener addCategoryListener = new OnClickListener() {

		public void onClick(View v) {

			launchModifyCategoryActivity(null);

		}
	};

	private OnItemClickListener categoryClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Category toModify = listContent.getItem((int) arg3);
			launchModifyCategoryActivity(toModify);
		}

	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categories_list);

		// Add new category button event listener
		Button addCategoryButton = (Button) findViewById(R.id.new_category_button);
		addCategoryButton.setOnClickListener(addCategoryListener);

		// Fill in the list
		listContent = new CategoryListAdapter(getApplicationContext());

		ListView lv = (ListView) findViewById(R.id.categories_list_listView);
		lv.setAdapter(listContent);
		registerForContextMenu(lv);
		lv.setOnItemClickListener(categoryClickListener);
	}

	@Override
	public void onResume() {

		// Force an update of the list view. Will do for now
		listContent.notifyDataSetChanged();
		super.onResume();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		System.err.println("Long click triggered");

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		ListView lV = (ListView) v;

		Category selectedItem = (Category) lV.getAdapter().getItem(info.position);
		menu.setHeaderTitle(selectedItem.name);

		menu.add("Delete");

		super.onCreateContextMenu(menu, v, menuInfo);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		Category attatchedCategory = listContent.getItem((int) info.id);

		if (item.getTitle().equals("Delete")) {

			CategoryCalDB db = ((CategoryCalendar) getApplication()).getDB();
			db.deleteCategory(attatchedCategory.id);
			listContent.notifyDataSetChanged();
		} else if (item.getTitle().equals("Modify")) { // Relic of an older
														// system

			launchModifyCategoryActivity(attatchedCategory);

		}

		return true;

	}

	private void launchModifyCategoryActivity(Category input) {

		Intent change = new Intent();

		ComponentName actName = new ComponentName("com.DeezApps.Agenda_Calendar",
				"com.DeezApps.Agenda_Calendar.ModifyCategory");

		change.setComponent(actName);

		if (input != null) {

			change.putExtra("Modify", input.id);
		}

		startActivity(change);
	}
}