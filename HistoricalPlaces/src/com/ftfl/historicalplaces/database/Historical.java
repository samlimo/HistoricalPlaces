package com.ftfl.historicalplaces.database;


public class Historical {

	// ------------------ Variables ------------------- //

	private String mHistoricalPlace;
	private String mDescription;
	private String mAddress;
	private String mDistrict;
	private String mWeeklyCloseDay;
	private String mDailyVisitTime;
	private String mLatitute;
	private String mLongitude;
	private String mID;
	private String mImagePath = "";

	// ------------------ Constructor ------------------- //
	public Historical(String mHistoricalPlace, String mDescription,
			String mAddress, String mDistrict, String mWeeklyCloseDay,
			String mDailyVisitTime, String mLatitute, String mLongitude,
			String mID, String mImagePath) {
		super();
		this.mHistoricalPlace = mHistoricalPlace;
		this.mDescription = mDescription;
		this.mAddress = mAddress;
		this.mDistrict = mDistrict;
		this.mWeeklyCloseDay = mWeeklyCloseDay;
		this.mDailyVisitTime = mDailyVisitTime;
		this.mLatitute = mLatitute;
		this.mLongitude = mLongitude;
		this.mID = mID;
		this.mImagePath = mImagePath;
	}
	
	public Historical(String mHistoricalPlace, String mDescription,
			String mAddress, String mDistrict, String mWeeklyCloseDay,
			String mDailyVisitTime, String mLatitute, String mLongitude) {
		super();
		this.mHistoricalPlace = mHistoricalPlace;
		this.mDescription = mDescription;
		this.mAddress = mAddress;
		this.mDistrict = mDistrict;
		this.mWeeklyCloseDay = mWeeklyCloseDay;
		this.mDailyVisitTime = mDailyVisitTime;
		this.mLatitute = mLatitute;
		this.mLongitude = mLongitude;
	}

	public Historical() {
		super();
	}

	public Historical(String mHistoricalPlace, String mDescription,
			String mAddress, String mDistrict, String mWeeklyCloseDay,
			String mDailyVisitTime, String mLatitute, String mLongitude,
			String mID) {
		super();
		this.mHistoricalPlace = mHistoricalPlace;
		this.mDescription = mDescription;
		this.mAddress = mAddress;
		this.mDistrict = mDistrict;
		this.mWeeklyCloseDay = mWeeklyCloseDay;
		this.mDailyVisitTime = mDailyVisitTime;
		this.mLatitute = mLatitute;
		this.mLongitude = mLongitude;
		this.mID = mID;
	}

	// ------------------ Getter Setter ------------------- //
	public String getmHistoricalPlace() {
		return mHistoricalPlace;
	}

	public void setmHistoricalPlace(String mHistoricalPlace) {
		this.mHistoricalPlace = mHistoricalPlace;
	}

	public String getmDescription() {
		return mDescription;
	}

	public void setmDescription(String mDescription) {
		this.mDescription = mDescription;
	}

	public String getmAddress() {
		return mAddress;
	}

	public void setmAddress(String mAddress) {
		this.mAddress = mAddress;
	}

	public String getmDistrict() {
		return mDistrict;
	}

	public void setmDistrict(String mDistrict) {
		this.mDistrict = mDistrict;
	}

	public String getmWeeklyCloseDay() {
		return mWeeklyCloseDay;
	}

	public void setmWeeklyCloseDay(String mWeeklyCloseDay) {
		this.mWeeklyCloseDay = mWeeklyCloseDay;
	}

	public String getmDailyVisitTime() {
		return mDailyVisitTime;
	}

	public void setmDailyVisitTime(String mDailyVisitTime) {
		this.mDailyVisitTime = mDailyVisitTime;
	}

	public String getmLatitute() {
		return mLatitute;
	}

	public void setmLatitute(String mLatitute) {
		this.mLatitute = mLatitute;
	}

	public String getmLongitude() {
		return mLongitude;
	}

	public void setmLongitude(String mLongitude) {
		this.mLongitude = mLongitude;
	}

	public String getmID() {
		return mID;
	}

	public void setmID(String mID) {
		this.mID = mID;
	}

	public String getmImagePath() {
		return mImagePath;
	}

	public void setmImagePath(String mImagePath) {
		this.mImagePath = mImagePath;
	}

	
	
}
