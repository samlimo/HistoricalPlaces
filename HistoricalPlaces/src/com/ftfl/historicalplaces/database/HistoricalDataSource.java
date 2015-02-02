package com.ftfl.historicalplaces.database;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HistoricalDataSource {

	// Database fields
	private SQLiteDatabase placesDatabase;
	private HistoricalSQLiteHelper placesDbHelper;
	List<Historical> placesList = new ArrayList<Historical>();

	public HistoricalDataSource(Context context) {
		placesDbHelper = new HistoricalSQLiteHelper(context);
	}

	/*
	 * open a method for writable database
	 */
	public void open() throws SQLException {
		placesDatabase = placesDbHelper.getWritableDatabase();
	}

	/*
	 * close database connection
	 */
	public void close() {
		placesDbHelper.close();
	}

	/*
	 * insert data into the database.
	 */

	public boolean insert(Historical insertPlace) {

		this.open();

		ContentValues cv = new ContentValues();

		cv.put(HistoricalSQLiteHelper.COL_PLACE, insertPlace.getmHistoricalPlace());
		cv.put(HistoricalSQLiteHelper.COL_ADDRESS, insertPlace.getmAddress());
		cv.put(HistoricalSQLiteHelper.COL_DESCRIPTION,
				insertPlace.getmDescription());
		cv.put(HistoricalSQLiteHelper.COL_LATTITUDE, insertPlace.getmLatitute());
		cv.put(HistoricalSQLiteHelper.COL_LONGITUDE, insertPlace.getmLongitude());
		cv.put(HistoricalSQLiteHelper.COL_WEEKLY_CLOSED_DAY,
				insertPlace.getmDailyVisitTime());
		cv.put(HistoricalSQLiteHelper.COL_DAILY_VISIT_TIME,
				insertPlace.getmWeeklyCloseDay());
		cv.put(HistoricalSQLiteHelper.COL_DISTRICT, insertPlace.getmDistrict());

		long check = placesDatabase.insert(HistoricalSQLiteHelper.TABLE_PLACES,
				null, cv);
		placesDatabase.close();

		this.close();
		if (check < 0)
			return false;
		else
			return true;
	}

	// Updating database by mId
	public boolean updateData(long eId, Historical updatePlace) {
		this.open();
		ContentValues cv = new ContentValues();

		cv.put(HistoricalSQLiteHelper.COL_PLACE, updatePlace.getmHistoricalPlace());
		cv.put(HistoricalSQLiteHelper.COL_DESCRIPTION, updatePlace.getmAddress());
		cv.put(HistoricalSQLiteHelper.COL_ADDRESS, updatePlace.getmDescription());
		cv.put(HistoricalSQLiteHelper.COL_LATTITUDE, updatePlace.getmLatitute());
		cv.put(HistoricalSQLiteHelper.COL_LONGITUDE, updatePlace.getmLongitude());
		cv.put(HistoricalSQLiteHelper.COL_WEEKLY_CLOSED_DAY,
				updatePlace.getmDailyVisitTime());
		cv.put(HistoricalSQLiteHelper.COL_DAILY_VISIT_TIME,
				updatePlace.getmWeeklyCloseDay());
		cv.put(HistoricalSQLiteHelper.COL_DISTRICT, updatePlace.getmDistrict());

		int check = placesDatabase.update(HistoricalSQLiteHelper.TABLE_PLACES, cv,
				HistoricalSQLiteHelper.COL_ID + "=" + eId, null);
		placesDatabase.close();

		this.close();
		if (check == 0)
			return false;
		else
			return true;
	}

	// delete data form database.
	public boolean deleteData(long eId) {
		this.open();
		try {
			placesDatabase.delete(HistoricalSQLiteHelper.TABLE_PLACES,
					HistoricalSQLiteHelper.COL_ID + "=" + eId, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
			return false;
		}
		this.close();
		return true;
	}

	/*
	 * using cursor for display data from database.
	 */
	public List<Historical> placesData() {
		this.open();

		Cursor cursor = placesDatabase.query(HistoricalSQLiteHelper.TABLE_PLACES,
				new String[] { HistoricalSQLiteHelper.COL_ID,
						HistoricalSQLiteHelper.COL_PLACE,
						HistoricalSQLiteHelper.COL_DESCRIPTION,
						HistoricalSQLiteHelper.COL_ADDRESS,
						HistoricalSQLiteHelper.COL_LATTITUDE,
						HistoricalSQLiteHelper.COL_LONGITUDE,
						HistoricalSQLiteHelper.COL_WEEKLY_CLOSED_DAY,
						HistoricalSQLiteHelper.COL_DAILY_VISIT_TIME,
						HistoricalSQLiteHelper.COL_DISTRICT }, null, null, null,
				null, null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {

				do {

					String mId = cursor.getString(cursor
							.getColumnIndex(HistoricalSQLiteHelper.COL_ID));
					String mName = cursor.getString(cursor
							.getColumnIndex(HistoricalSQLiteHelper.COL_PLACE));
					String mPurpose = cursor
							.getString(cursor
									.getColumnIndex(HistoricalSQLiteHelper.COL_DESCRIPTION));
					String mAddress = cursor.getString(cursor
							.getColumnIndex(HistoricalSQLiteHelper.COL_ADDRESS));
					String mLatitude = cursor.getString(cursor
							.getColumnIndex(HistoricalSQLiteHelper.COL_LATTITUDE));
					String mLongitude = cursor.getString(cursor
							.getColumnIndex(HistoricalSQLiteHelper.COL_LONGITUDE));
					String mStartDay = cursor
							.getString(cursor
									.getColumnIndex(HistoricalSQLiteHelper.COL_WEEKLY_CLOSED_DAY));
					String mEndDay = cursor
							.getString(cursor
									.getColumnIndex(HistoricalSQLiteHelper.COL_DAILY_VISIT_TIME));
					String mNotes = cursor.getString(cursor
							.getColumnIndex(HistoricalSQLiteHelper.COL_DISTRICT));

					placesList.add(new Historical(mId, mName, mPurpose,
							mAddress, mLatitude, mLongitude, mStartDay,
							mEndDay, mNotes));
				} while (cursor.moveToNext());
			}
			cursor.close();
		}
		this.close();
		return placesList;
	}

	/*
	 * create a profile of ICareProfile. Here the data of the database according
	 * to the given mId is set to the profile and return a profile.
	 */
	public Historical singlePlaceData(long mActivityId) {
		this.open();
		Historical informationObject;
		String mId;
		String mPlace;
		String mDescription;
		String mAddress;
		String mLatitude;
		String mLongitude;
		String mWeeklyClosedDay;
		String mDailyVisitTime;
		String mDistrict;

		Cursor mUpdateCursor = placesDatabase.query(
				HistoricalSQLiteHelper.TABLE_PLACES, new String[] {
						HistoricalSQLiteHelper.COL_ID,
						HistoricalSQLiteHelper.COL_PLACE,
						HistoricalSQLiteHelper.COL_DESCRIPTION,
						HistoricalSQLiteHelper.COL_ADDRESS,
						HistoricalSQLiteHelper.COL_LATTITUDE,
						HistoricalSQLiteHelper.COL_LONGITUDE,
						HistoricalSQLiteHelper.COL_WEEKLY_CLOSED_DAY,
						HistoricalSQLiteHelper.COL_DAILY_VISIT_TIME,
						HistoricalSQLiteHelper.COL_DISTRICT, },
				HistoricalSQLiteHelper.COL_ID + "=" + mActivityId, null, null,
				null, null);

		mUpdateCursor.moveToFirst();

		mId = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(HistoricalSQLiteHelper.COL_ID));
		mPlace = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(HistoricalSQLiteHelper.COL_PLACE));
		mDescription = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(HistoricalSQLiteHelper.COL_DESCRIPTION));
		mAddress = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(HistoricalSQLiteHelper.COL_ADDRESS));
		mLatitude = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(HistoricalSQLiteHelper.COL_LATTITUDE));
		mLongitude = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(HistoricalSQLiteHelper.COL_LONGITUDE));
		mWeeklyClosedDay = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(HistoricalSQLiteHelper.COL_WEEKLY_CLOSED_DAY));
		mDailyVisitTime = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(HistoricalSQLiteHelper.COL_DAILY_VISIT_TIME));
		mDistrict = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(HistoricalSQLiteHelper.COL_DISTRICT));
		mUpdateCursor.close();
		informationObject = new Historical(mId, mPlace, mDescription, mAddress,
				mLatitude, mLongitude, mWeeklyClosedDay, mDailyVisitTime,
				mDistrict);
		this.close();
		return informationObject;
	}

	public boolean isEmpty() {
		this.open();
		Cursor mCursor = placesDatabase.query(HistoricalSQLiteHelper.TABLE_PLACES,
				new String[] { HistoricalSQLiteHelper.COL_ID,
						HistoricalSQLiteHelper.COL_PLACE,
						HistoricalSQLiteHelper.COL_DESCRIPTION,
						HistoricalSQLiteHelper.COL_ADDRESS,
						HistoricalSQLiteHelper.COL_LATTITUDE,
						HistoricalSQLiteHelper.COL_LONGITUDE,
						HistoricalSQLiteHelper.COL_WEEKLY_CLOSED_DAY,
						HistoricalSQLiteHelper.COL_DAILY_VISIT_TIME,
						HistoricalSQLiteHelper.COL_DISTRICT }, null, null, null,
				null, null);
		if (mCursor.getCount() == 0) {
			this.close();
			return true;
		}

		else {
			this.close();
			return false;
		}
	}

}
