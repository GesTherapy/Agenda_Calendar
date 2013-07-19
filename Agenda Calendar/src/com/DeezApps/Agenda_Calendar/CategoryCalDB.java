package com.DeezApps.Agenda_Calendar;
/**
 * * based on open source github library from
 * @author Matthew Moss
 *
 ** further developed for Agenda Calendar
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CategoryCalDB {

	int id = 0;
	public static final String KEY_ROWID = "_id";

	public static final String KEY_CATEGORY_NAME = "Name";
	public static final String KEY_CATEGORY_SYMBOL = "Symbol";
	public static final String KEY_CATEGORY_COLOR = "Color";
	public static final String KEY_CATEGORY_COMMENT = "Comment";

	public static final String KEY_DATES_YEAR = "Year";
	public static final String KEY_DATES_MONTH = "Month";
	public static final String KEY_DATES_DAY = "Day";
	public static final String KEY_DATES_CATEGORY1ID = "Category1ID";
	public static final String KEY_DATES_CATEGORY2ID = "Category2ID";
	public static final String KEY_DATES_CATEGORY3ID = "Category3ID";
	public static final String KEY_DATES_CATEGORY4ID = "Category4ID";

	private static final String DATABASE_NAME = "CategoryCalDB";

	private static final String DATABASE_CATEGORY_TABLE = "tblCategoryTypes";
	private static final String DATABASE_DATES_TABLE = "tblDates";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_TAG = "CategoryCalDB";

	private static final String TABLE_CREATE_CATEGORY = "CREATE TABLE "
			+ DATABASE_CATEGORY_TABLE + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_CATEGORY_NAME
			+ " TEXT, " + KEY_CATEGORY_SYMBOL + " TEXT, " + KEY_CATEGORY_COLOR
			+ " INT, " + KEY_CATEGORY_COMMENT + " TEXT);";

	private static final String TABLE_CREATE_DATES = "CREATE TABLE "
			+ DATABASE_DATES_TABLE + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DATES_YEAR + " INT,"
			+ KEY_DATES_MONTH + " INT," + KEY_DATES_DAY + " INT,"
			+ KEY_DATES_CATEGORY1ID + " INT," + KEY_DATES_CATEGORY2ID + " INT,"
			+ KEY_DATES_CATEGORY3ID + " INT," + KEY_DATES_CATEGORY4ID + " INT);";

	@SuppressWarnings("unused")
	private final Context context;

	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public CategoryCalDB(Context context) {

		this.context = context;
		this.DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {

			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(TABLE_CREATE_CATEGORY);
			db.execSQL(TABLE_CREATE_DATES);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			Log.w(DATABASE_TAG, "Upgrading database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data");

		}

	}

	public CategoryCalDB open() throws SQLException {

		this.db = DBHelper.getWritableDatabase();
		return this;
	}

	public CategoryCalDB openRead() throws SQLException {

		this.db = DBHelper.getReadableDatabase();
		return this;
	}

	public void close() {

		DBHelper.close();

	}

	public void insertCategory(Category sh) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_CATEGORY_NAME, sh.name);
		initialValues.put(KEY_CATEGORY_SYMBOL, sh.symbol);
		initialValues.put(KEY_CATEGORY_COLOR, sh.color);
		initialValues.put(KEY_CATEGORY_COMMENT, sh.comments);

		this.open();
		db.insert(DATABASE_CATEGORY_TABLE, null, initialValues);
		this.close();
	}

	public void deleteCategory(int id) {

		String whereClause = KEY_ROWID + " = " + Integer.toString(id);

		this.open();
		db.delete(DATABASE_CATEGORY_TABLE, whereClause, null);
		this.close();

	}

	public void updateCategory(int oldId, Category sh) {

		this.open();

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_CATEGORY_NAME, sh.name);
		initialValues.put(KEY_CATEGORY_SYMBOL, sh.symbol);
		initialValues.put(KEY_CATEGORY_COLOR, sh.color);
		initialValues.put(KEY_CATEGORY_COMMENT, sh.comments);

		String whereClause = KEY_ROWID + "=" + oldId;

		db.update(DATABASE_CATEGORY_TABLE, initialValues, whereClause, null);

		this.close();
	}

	public int getEntryCount() {

		this.openRead();
		Cursor cursor = db.query(true, DATABASE_CATEGORY_TABLE, null, "", null,
				"", "", "", "");
		int entries = cursor.getCount();
		cursor.close();
		this.close();

		return entries;

	}

	public Category getCategoryByID(int id) {

		String[] colList = { KEY_CATEGORY_NAME, KEY_CATEGORY_SYMBOL, KEY_CATEGORY_COLOR,
				KEY_CATEGORY_COMMENT, KEY_ROWID };

		String whereClause = KEY_ROWID + " = " + Integer.toString(id);

		this.openRead();

		Cursor cursor = db.query(true, DATABASE_CATEGORY_TABLE, colList,
				whereClause, null, null, null, null, null);

		Category ret;

		if (cursor.moveToFirst()) {
			ret = new Category();
			ret.name = cursor.getString(cursor.getColumnIndex(KEY_CATEGORY_NAME));
			ret.symbol = cursor.getString(cursor
					.getColumnIndex(KEY_CATEGORY_SYMBOL));
			ret.color = cursor.getInt(cursor.getColumnIndex(KEY_CATEGORY_COLOR));
			ret.comments = cursor.getString(cursor
					.getColumnIndex(KEY_CATEGORY_COMMENT));
			ret.id = cursor.getInt(cursor.getColumnIndex(KEY_ROWID));

		} else {

			ret = null;
		}

		cursor.close();
		this.close();

		return ret;
	}

	public List<Category> getAllCategories() {

		String[] colList = { KEY_CATEGORY_NAME, KEY_CATEGORY_SYMBOL, KEY_CATEGORY_COLOR,
				KEY_CATEGORY_COMMENT, KEY_ROWID };

		this.openRead();

		List<Category> allCategoriesList = new ArrayList<Category>();

		Cursor cursor = db.query(true, DATABASE_CATEGORY_TABLE, colList, null,
				null, null, null, null, null);

		while (cursor.moveToNext()) {
			Category sh = new Category();
			sh.name = cursor.getString(cursor.getColumnIndex(KEY_CATEGORY_NAME));
			sh.symbol = cursor.getString(cursor
					.getColumnIndex(KEY_CATEGORY_SYMBOL));
			sh.color = cursor.getInt(cursor.getColumnIndex(KEY_CATEGORY_COLOR));
			sh.comments = cursor.getString(cursor
					.getColumnIndex(KEY_CATEGORY_COMMENT));
			sh.id = cursor.getInt(cursor.getColumnIndex(KEY_ROWID));

			allCategoriesList.add(sh);
		}

		cursor.close();
		this.close();

		return allCategoriesList;
	}

	public Category getCategory1ByDate(Date d) {
		String[] colList = { KEY_DATES_CATEGORY1ID };
		String selection = KEY_DATES_YEAR + " = "
				+ Integer.toString(d.getYear() + 1900) + " AND "
				+ KEY_DATES_MONTH + " = " + Integer.toString(d.getMonth())
				+ " AND " + KEY_DATES_DAY + " = "
				+ Integer.toString(d.getDate());
		this.openRead();
		Cursor cursor = db.query(true, DATABASE_DATES_TABLE, colList,
				selection, null, null, null, null, null);
		Category s;
		if (cursor.moveToFirst()) {
			s = getCategoryByID(cursor.getInt(cursor
					.getColumnIndex(KEY_DATES_CATEGORY1ID)));
		} else {
			s = null;
		}
		cursor.close();
		this.close();
		return s;
	}
	
	public Category getCategory2ByDate(Date d) {
		String[] colList = { KEY_DATES_CATEGORY2ID };
		String selection = KEY_DATES_YEAR + " = "
				+ Integer.toString(d.getYear() + 1900) + " AND "
				+ KEY_DATES_MONTH + " = " + Integer.toString(d.getMonth())
				+ " AND " + KEY_DATES_DAY + " = "
				+ Integer.toString(d.getDate());
		this.openRead();
		Cursor cursor = db.query(true, DATABASE_DATES_TABLE, colList,
				selection, null, null, null, null, null);
		Category s;
		if (cursor.moveToFirst()) {
			s = getCategoryByID(cursor.getInt(cursor
					.getColumnIndex(KEY_DATES_CATEGORY2ID)));
		} else {
			s = null;
		}
		cursor.close();
		this.close();
		return s;
	}
	
	public Category getCategory3ByDate(Date d) {
		String[] colList = { KEY_DATES_CATEGORY3ID };
		String selection = KEY_DATES_YEAR + " = "
				+ Integer.toString(d.getYear() + 1900) + " AND "
				+ KEY_DATES_MONTH + " = " + Integer.toString(d.getMonth())
				+ " AND " + KEY_DATES_DAY + " = "
				+ Integer.toString(d.getDate());
		this.openRead();
		Cursor cursor = db.query(true, DATABASE_DATES_TABLE, colList,
				selection, null, null, null, null, null);
		Category s;
		if (cursor.moveToFirst()) {
			s = getCategoryByID(cursor.getInt(cursor
					.getColumnIndex(KEY_DATES_CATEGORY3ID)));
		} else {
			s = null;
		}
		cursor.close();
		this.close();
		return s;
	}
	
	public Category getCategory4ByDate(Date d) {
		String[] colList = { KEY_DATES_CATEGORY4ID };
		String selection = KEY_DATES_YEAR + " = "
				+ Integer.toString(d.getYear() + 1900) + " AND "
				+ KEY_DATES_MONTH + " = " + Integer.toString(d.getMonth())
				+ " AND " + KEY_DATES_DAY + " = "
				+ Integer.toString(d.getDate());
		this.openRead();
		Cursor cursor = db.query(true, DATABASE_DATES_TABLE, colList,
				selection, null, null, null, null, null);
		Category s;
		if (cursor.moveToFirst()) {
			s = getCategoryByID(cursor.getInt(cursor
					.getColumnIndex(KEY_DATES_CATEGORY4ID)));
		} else {
			s = null;
		}
		cursor.close();
		this.close();
		return s;
	}

	public List<Day> getMonthCategories(Date ym) {

		List<Day> daysList = new ArrayList<Day>();

		String[] colList = { KEY_DATES_DAY, KEY_DATES_CATEGORY1ID, KEY_DATES_CATEGORY2ID, KEY_DATES_CATEGORY3ID
								, KEY_DATES_CATEGORY4ID};

		String selection = KEY_DATES_YEAR + " = "
				+ Integer.toString(ym.getYear() + 1900) + " AND "
				+ KEY_DATES_MONTH + " = " + Integer.toString(ym.getMonth());

		this.openRead();

		Cursor cursor = db.query(true, DATABASE_DATES_TABLE, colList,
				selection, null, null, null, null, null);

		while (cursor.moveToNext()) {

			Day d = new Day();
			d.date = (Date) ym.clone();
			d.date.setDate(cursor.getInt(cursor.getColumnIndex(KEY_DATES_DAY)));
			d.category1Id = cursor.getInt(cursor.getColumnIndex(KEY_DATES_CATEGORY1ID));
			d.category2Id = cursor.getInt(cursor.getColumnIndex(KEY_DATES_CATEGORY2ID));
			d.category3Id = cursor.getInt(cursor.getColumnIndex(KEY_DATES_CATEGORY3ID));
			d.category4Id = cursor.getInt(cursor.getColumnIndex(KEY_DATES_CATEGORY4ID));

			Category s1 = this.getCategoryByID(d.category1Id);
			if (s1 != null) {
				d.category1Symbol = s1.symbol;
				d.category1Color = s1.color;
				daysList.add(d);
			}
			Category s2 = this.getCategoryByID(d.category2Id);
			if (s2 != null) {
				d.category2Symbol = s2.symbol;
				d.category2Color = s2.color;
				daysList.add(d);
			}	
			Category s3 = this.getCategoryByID(d.category3Id);
			if (s3 != null) {
				d.category3Symbol = s3.symbol;
				d.category3Color = s3.color;
				daysList.add(d);
			}
			Category s4 = this.getCategoryByID(d.category4Id);
			if (s4 != null) {
				d.category4Symbol = s4.symbol;
				d.category4Color = s4.color;
				daysList.add(d);
			}
		}

		cursor.close();
		this.close();

		return daysList;
	}

	public void setDayCategory(Day d) {

		ContentValues v = new ContentValues();
		v.put(KEY_DATES_DAY, d.date.getDate());
		v.put(KEY_DATES_MONTH, d.date.getMonth());
		v.put(KEY_DATES_YEAR, d.date.getYear() + 1900);
		v.put(KEY_DATES_CATEGORY1ID, d.category1Id);
		v.put(KEY_DATES_CATEGORY2ID, d.category2Id);
		v.put(KEY_DATES_CATEGORY3ID, d.category3Id);
		v.put(KEY_DATES_CATEGORY4ID, d.category4Id);

		this.open();
		db.insert(DATABASE_DATES_TABLE, null, v);
		this.close();

	}

	public void clearDay(Date d) {

		String whereClause = KEY_DATES_YEAR + " = "
				+ Integer.toString(d.getYear() + 1900) + " AND "
				+ KEY_DATES_MONTH + " = " + Integer.toString(d.getMonth())
				+ " AND " + KEY_DATES_DAY + " = "
				+ Integer.toString(d.getDate());

		this.open();
		db.delete(DATABASE_DATES_TABLE, whereClause, null);
		this.close();
	}
}