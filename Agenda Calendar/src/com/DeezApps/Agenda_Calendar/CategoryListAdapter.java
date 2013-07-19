package com.DeezApps.Agenda_Calendar;
/**
 * * based on open source github library from
 * @author Matthew Moss
 *
 ** further developed for Agenda Calendar
 */
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoryListAdapter extends BaseAdapter {

	private Context context;
	private CategoryCalDB db;
	private List<Category> categoryList;

	// Add database connection on create?
	public CategoryListAdapter(Context context) {

		this.context = context;
		this.db = ((CategoryCalendar) context.getApplicationContext()).getDB();
		this.categoryList = db.getAllCategories();
	}

	public int getCount() {

		int entryCount = this.db.getEntryCount();

		return entryCount;
	}

	public Category getItem(int id) {

		Category ret;

		if (id >= 0 && id < categoryList.size()) {

			ret = categoryList.get(id);
		} else {
			ret = null;
		}

		return ret;
	}

	public long getItemId(int item) {
		// WTF alert :/
		return item;
	}

	public View getView(int position, View convertView, ViewGroup viewGroup) {

		if (convertView == null) {
			LayoutInflater inflator = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflator.inflate(R.layout.category_bar, null);
		}

		Category result = getItem(position);

		if (result != null) {

			convertView.setVisibility(View.VISIBLE);

			TextView nameField = (TextView) convertView
					.findViewById(R.id.category_name);
			nameField.setText(result.name);

			TextView symbolField = (TextView) convertView
					.findViewById(R.id.category_symbol);
			symbolField.setText(result.symbol);
			symbolField.setTextColor(result.color);

		} else {

			convertView.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	public void notifyDataSetChanged() {

		this.categoryList = db.getAllCategories();
		super.notifyDataSetChanged();
	}
}