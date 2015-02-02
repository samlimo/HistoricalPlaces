package com.ftfl.historicalplaces.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HistoricalSQLiteHelper extends SQLiteOpenHelper {
	// ICare Profile Table
	public static final String TABLE_PLACES = "places";
	public static final String COL_ID = "mId";
	public static final String COL_PLACE = "mName";
	public static final String COL_DESCRIPTION = "purpose";
	public static final String COL_ADDRESS = "address";
	public static final String COL_LATTITUDE = "latitude";
	public static final String COL_LONGITUDE = "longitude";
	public static final String COL_WEEKLY_CLOSED_DAY = "start_day";
	public static final String COL_DAILY_VISIT_TIME = "end_day";
	public static final String COL_DISTRICT = "notes";

	private static final String DATABASE_NAME = "HistoricalPlaces.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE_CHART = "create table "
			+ TABLE_PLACES + "( " + COL_ID
			+ " integer primary key autoincrement, " + " " + COL_PLACE
			+ " text not null," + " " + COL_DESCRIPTION + " text not null,"
			+ " " + COL_ADDRESS + " text not null," + " " + COL_LATTITUDE
			+ " text not null," + " " + COL_LONGITUDE + " text not null," + " "
			+ COL_WEEKLY_CLOSED_DAY + " text not null," + " "
			+ COL_DAILY_VISIT_TIME + " text not null," + " " + COL_DISTRICT
			+ " text not null);";

	public HistoricalSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_CHART);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(HistoricalSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
		onCreate(db);
	}

}
