package com.example.stpl.workloc;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class Entity {
	public static String DATABASE_NAME = "Mileage Claim";
	public static int DATABASE_VERSION = 1;
	public static String LIVETRIPTABLE = "LiveTrip";
	public static String TRIPERPLOG = "Triperplog";
	public static String TRIPS = "Trips";
	public static String SPEED = "vehicleSpeed";
	public static String LON = "vehicleLongitude";
	public static String LOCATION = "vehicleLocation";
	public static String LAT = "vehicleLatitude";

	public static String ERP_LAT = "erp_lat";
	public static String ERP_LONG = "erp_long";
	public static String ERP_ROADNAME = "roadname";
	public static String ERP_TIME = "erptime";
	public static String ERPGANTRY_ID = "erpgantry_id";
	public static String ERPGANTRYUNIQUEID = "erpgantryuniqueid";
	public static String TIMESTAMP = "timestamp";

	public static String USERID = "userId";
	public static String TRIPID = "tripId";
	public static String DATE = "Date";
	public static String TIME = "timeStamp";
	public static String DISTANCE = "distance";
	public static String TRIPLOG = "TripLog";
	public static String PARKTRIP = "ParkTrip";
	public static String PAYMODE = "payMode";
	public static String INFO = "parkInfo";
	public static String FEE = "parkFee";
	public static String STARTTIME = "starttime";
	public static String DISTANCECLAIMED = "distanceclaimed";
	public static String DISTANCECOVERED = "distancecovered";
	public static String DISTANCECOVEREDBYAPP = "distancecoveredbyapp";
	public static String REIMBURSEMENTRATE = "reimbursement";
	public static String TRIPCOMMENT = "tripcomment";
	public static String TRIPPURPOSE = "trippurpose";
	public static String TRIPTYPE = "triptype";
	public static String VEHICLENUMBER = "vehiclenumber";
	public static String ISEXPORTED = "isexported";
	public static String TOTALAMOUNT = "totalamount";
	public static String ODOMETEREND = "odometerend";
	public static String NOTE = "note";
	public static String ODOMETERSTART = "odometerstart";
	public static String HTOD = "htod";
	public static String STARTLOCATION = "startlocation";
	public static String STARTEDFROM = "startedfrom";
	public static String DRIVERNAME = "drivername";
	public static String MILEAGECLAIMED = "mileageclaimed";
	public static String ENDTIME = "endtime";
	public static String ENDLOCATION = "endlocation";
	public static String ERP_LOCATIONS = "erp_locations";
	public static String SERVER_ERP_ID = "server_erp_id";
	public static String ZONE_ID = "zone_id";
	public static String GANTRY_ID = "gantry_id";
	public static String GANTRY_NAME = "gantry_name";
	public static String LONGITUDE = "longitude";
	public static String LATITUDE = "latitude";
	public static String ERP_FEES = "erpfees";
	public static String SERVER_ERP_FEE_ID = "servererpfeeid";
	public static String VCC_TYPE = "vcctype";
	public static String VCC_TYPE_ID = "vcctypeid";
	public static String DAY_TYPE = "daytype";
	public static String ERP_FEE = "erp_fee";
	public static String COSLAT = "caslat";
	public static String SINLAT = "sinlat";
	public static String COSLNG = "coslng";
	public static String SINLNG = "sinlng";
	public Context context;
	static DbHelper dbhelper;
	SQLiteDatabase myDataBase;

	public Entity(Context context) {
		this.context = context;
	}

	public Entity open() throws SQLException {

		dbhelper = new DbHelper(context).getIntence(context);
		myDataBase = dbhelper.getWritableDatabase();

		return this;
	}

	public void close() {
		dbhelper.close();
	}

	public boolean isCategoryIDExit(String id) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + LIVETRIPTABLE
				+ " where tripId=" + id, null);
		if (c.getCount() > 0) {
			return true;
		}
		return false;
	}

	public void deleteCategory(String id) {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		db.execSQL("delete from " + LIVETRIPTABLE + " where tripId='" + id
				+ "'");
	}

	public void storeExtra(String speed, String longitude, String houseno,
			String lat, String userid, String tripid, String time,
			String distance) {
		ContentValues values = new ContentValues();
		values.put(SPEED, speed);
		values.put(LON, longitude);
		values.put(LOCATION, houseno);
		values.put(LAT, lat);
		values.put(USERID, userid);
		values.put(TRIPID, tripid);
		values.put(TIME, time);
		values.put(DISTANCE, distance);

		myDataBase.insert(LIVETRIPTABLE, null, values);

	}

	public void updateExtra(String id, String streetname, String houseno,
			String floor, String building, String country, String countrycode,
			String distance) {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(SPEED, id);
		values.put(LON, streetname);
		values.put(LOCATION, houseno);
		values.put(LAT, floor);
		values.put(USERID, building);
		values.put(TRIPID, country);
		values.put(TIME, countrycode);
		values.put(DISTANCE, distance);
		db.update(LIVETRIPTABLE, values, "tripId=?",
				new String[] { String.valueOf(id) });

		// myDataBase.insert(CATEGARY_TABLE, null, values);

	}

	public Cursor fetchExtra() {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + LIVETRIPTABLE + " ORDER BY "
				+ TIME + " DESC ", null);
		if (c != null)
			c.moveToFirst();
		return c;
	}

	public Cursor fetchExtraid(int id) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + LIVETRIPTABLE
				+ " where tripId=" + id + " ORDER BY " + TIME + " DESC", null);
		if (c != null)
			c.moveToFirst();
		return c;
	}

	public void storeTripLog(String userid, String tripid, String time,
			String floor, String speed, String longitude, String houseno,
			String lat, String distance) {
		ContentValues values = new ContentValues();
		values.put(USERID, userid);
		values.put(TRIPID, tripid);
		values.put(TIME, time);
		values.put(SPEED, speed);
		values.put(LON, longitude);
		values.put(LOCATION, houseno);
		values.put(LAT, lat);
		values.put(DISTANCE, distance);
		myDataBase.insert(TRIPLOG, null, values);

	}

	public Cursor fetchTrip() {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + TRIPLOG, null);
		if (c != null)
			c.moveToFirst();
		return c;
	}

	public Cursor fetchTripid(String id) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + TRIPLOG + " where tripId="
				+ id, null);
		if (c != null)
			c.moveToFirst();
		return c;
	}

	public Cursor fetchSingleTripByid(String id) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery(
				"SELECT * FROM " + TRIPS + " where tripId=" + id, null);
		if (c != null)
			c.moveToFirst();
		return c;
	}

	public void storeTrips(String userid, String tripid, String note,
			String isexported, String distanceclaimed, String distancecovered,
			String distancecoveredbyapp, String drivername, String endlocation,
			String endtime, String mileageclaimed, String htod,
			String reimbursement, String totalamount, String odometerend,
			String odometerstart, String startedfrom, String startlocation,
			String starttime, String tripcomment, String trippurpose,
			String triptype, String vehiclenumber) {
		ContentValues values = new ContentValues();
		values.put(USERID, userid);
		values.put(TRIPID, tripid);
		values.put(NOTE, note);
		values.put(ISEXPORTED, isexported);
		values.put(DISTANCECLAIMED, distanceclaimed);
		values.put(DISTANCECOVERED, distancecovered);
		values.put(DISTANCECOVEREDBYAPP, distancecoveredbyapp);
		values.put(DRIVERNAME, drivername);
		values.put(ENDLOCATION, endlocation);
		values.put(ENDTIME, endtime);
		values.put(MILEAGECLAIMED, mileageclaimed);
		values.put(HTOD, htod);
		values.put(REIMBURSEMENTRATE, reimbursement);
		values.put(TOTALAMOUNT, totalamount);
		values.put(ODOMETEREND, odometerend);
		values.put(ODOMETERSTART, odometerstart);
		values.put(STARTEDFROM, startedfrom);
		values.put(STARTLOCATION, startlocation);
		values.put(STARTTIME, starttime);
		values.put(TRIPCOMMENT, tripcomment);
		values.put(TRIPPURPOSE, trippurpose);
		values.put(TRIPTYPE, triptype);
		values.put(VEHICLENUMBER, vehiclenumber);

		myDataBase.insert(TRIPS, null, values);
	}

	public void storeErpFees(String server_erp_fee_id, String vcc_type,
			String vcc_type_id, String day_type, String endtime,
			String starttime, String zone_id, String erp_fee) {
		ContentValues values = new ContentValues();
		values.put(SERVER_ERP_FEE_ID, server_erp_fee_id);
		values.put(VCC_TYPE, vcc_type);
		values.put(VCC_TYPE_ID, vcc_type_id);
		values.put(DAY_TYPE, day_type);
		values.put(ENDTIME, endtime);
		values.put(STARTTIME, starttime);
		values.put(ZONE_ID, zone_id);
		values.put(ERP_FEE, erp_fee);
		myDataBase.insert(ERP_FEES, null, values);
	}

	public void storeTripErpLog(String server_erp_fee_id, String vcc_type,
			String vcc_type_id, String day_type, String endtime,
			String starttime, String zone_id, String erp_fee) {
		ContentValues values = new ContentValues();
		values.put(SERVER_ERP_FEE_ID, server_erp_fee_id);
		values.put(VCC_TYPE, vcc_type);
		values.put(VCC_TYPE_ID, vcc_type_id);
		values.put(DAY_TYPE, day_type);
		values.put(ENDTIME, endtime);
		values.put(STARTTIME, starttime);
		values.put(ZONE_ID, zone_id);
		values.put(ERP_FEE, erp_fee);
		myDataBase.insert(ERP_FEES, null, values);
	}

	public int fetchZoneTax(String id, String vcctype, String daytype,
			String datetime) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();

		// Cursor c = db.rawQuery("SELECT * FROM " + ERP_FEES
		// +" where zone_id ='"+id+"' and vcctypeid ='"+vcctype+"' and starttime <= '"+datetime+"' and endtime >= '"+datetime+"'"
		// , null);
		Cursor c = db.rawQuery("SELECT * FROM " + ERP_FEES
				+ " where zone_id ='" + id + "' and vcctypeid ='" + vcctype
				+ "' and '" + datetime + "' BETWEEN starttime  and endtime ",
				null);

		if (c != null)
			c.moveToFirst();
		if (c.isAfterLast() == false) {
			int zone_fee = c.getInt(7);
			System.out.println("=======hhh=======" + zone_fee);
			return zone_fee;
		}
		// System.out.println("=============="+zone_fee);
		// System.out.println("zone_fee"+c.getString(c.getColumnIndex("endtime"))+"==============="+c.getString(c.getColumnIndex("starttime")));
		return 0;
	}

	public static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	public boolean fetchAllERPLocations() {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		// Cursor c = db.rawQuery("SELECT * FROM " + ERP_LOCATIONS, null);
		// if (c != null)
		// return true;
		// else
		// return false;

		Cursor mCount = db.rawQuery("select count(*) from " + ERP_LOCATIONS,
				null);
		mCount.moveToFirst();
		int count = mCount.getInt(0);
		mCount.close();
		if (count > 0) {
			return false;
		}
		return true;
	}

	public Cursor fetchSingleERPLocation(String id) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + ERP_LOCATIONS
				+ " where gantry_id=" + id, null);
		if (c != null)
			c.moveToFirst();
		return c;
	}

	public Cursor fetchAllERPFees() {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + ERP_FEES, null);
		if (c != null)
			c.moveToFirst();
		return c;
	}

	public Cursor fetchAllERPs() {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + ERP_LOCATIONS, null);
		if (c != null)
			c.moveToFirst();
		return c;
	}

	public Cursor fetchSingleERPFee(String id) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + ERP_FEES + " where tripId="
				+ id, null);
		if (c != null)
			c.moveToFirst();
		return c;
	}

	public void storeErpLocations(String server_erp_id, String zone_id,
			String gantry_id, String gantry_name, String longitude,
			String latitude) {
		ContentValues values = new ContentValues();
		values.put(SERVER_ERP_ID, server_erp_id);
		values.put(ZONE_ID, zone_id);
		values.put(GANTRY_ID, gantry_id);
		values.put(GANTRY_NAME, gantry_name);
		values.put(LONGITUDE, longitude);
		values.put(LATITUDE, latitude);

		values.put(COSLAT, Math.cos(deg2rad(Double.parseDouble(latitude))));
		values.put(SINLAT, Math.sin(deg2rad(Double.parseDouble(latitude))));
		values.put(COSLNG, Math.cos(deg2rad(Double.parseDouble(longitude))));
		values.put(SINLNG, Math.sin(deg2rad(Double.parseDouble(longitude))));
		myDataBase.insert(ERP_LOCATIONS, null, values);

	}



	public double convertPartialDistanceToKm(double result) {
		return Math.acos(result) * 6371;
	}

	public String buildDistanceQuery(double latitude, double longitude) {
		final double coslat = Math.cos(deg2rad(latitude));
		final double sinlat = Math.sin(deg2rad(latitude));
		final double coslng = Math.cos(deg2rad(longitude));
		final double sinlng = Math.sin(deg2rad(longitude));
		// @formatter:off
		return "(" + coslat + "*" + COSLAT + "*(" + COSLNG + "*" + coslng + "+"
				+ SINLNG + "*" + sinlng + ")+" + sinlat + "*" + SINLAT + ")";
		// @formatter:on
	}

	public void deleteERPLocations() {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		db.execSQL("delete  from " + ERP_LOCATIONS);
	}

	public void deleteERPFees() {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		db.execSQL("delete  from " + ERP_FEES);
	}

	public boolean storeTripERPLog(String date, String fees, String lat,
			String lon, String roadname, String erp_time, String gantry_id,
			String gantry_unique_id, String timestamp, String tripId,
			String userId) {
		ContentValues values = new ContentValues();
		values.put(DATE, date);
		values.put(ERP_FEE, fees);
		values.put(ERP_LAT, lat);
		values.put(ERP_LONG, lon);
		values.put(ERP_ROADNAME, roadname);
		values.put(ERP_TIME, erp_time);
		values.put(ERPGANTRY_ID, gantry_id);
		values.put(ERPGANTRYUNIQUEID, gantry_unique_id);
		values.put(TIMESTAMP, timestamp);
		values.put(TRIPID, tripId);
		values.put(USERID, userId);
		long id = myDataBase.insert(TRIPERPLOG, null, values);
		if (id > 0) {
			System.out.println("erp log inserted.");
			return true;
		} else {
			System.out.println("erp log not inserted.");
			return false;
		}
	}
	
	
	public String checkGantry(String id) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TRIPERPLOG + " where erpgantry_id='"
				+ id + "'", null);
		if(cursor!=null && cursor.getCount()>0)
			cursor.moveToFirst();
		else return null;
		
		return cursor.getString(cursor.getColumnIndex("Date"));
	}

	public void updateComment(String id, String comment) {
		ContentValues values = new ContentValues();
		values.put(TRIPID, id);
		values.put(TRIPCOMMENT, comment);
		int a = myDataBase
				.update(TRIPS, values, TRIPID + "='" + id + "'", null);
		System.out.println("updated at>>" + a);
	}

	public void updateERPLog(String id, String time, String fee, String name) {
		ContentValues values = new ContentValues();
		values.put(ERPGANTRYUNIQUEID, id);
		values.put(ERP_TIME, time);
		values.put(ERP_FEE, fee);
		values.put(ERP_ROADNAME, name);
		int a = myDataBase.update(TRIPERPLOG, values, ERPGANTRYUNIQUEID + "='"
				+ id + "'", null);
		System.out.println("updated at>>" + a);
	}

	public void deleteAllTrips() {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		db.execSQL("delete  from " + TRIPS);
	}

	public void storeParkFees(String userId, String tripId, String time,
			String payMode, String location, String info, String fee) {
		ContentValues values = new ContentValues();
		values.put(USERID, userId);
		values.put(TRIPID, tripId);
		values.put(TIME, time);
		values.put(PAYMODE, payMode);
		values.put(LOCATION, location);
		values.put(INFO, info);
		values.put(FEE, fee);
		long id;
		if (checkExist(tripId, userId)) {
			id = myDataBase.update(PARKTRIP, values, TRIPID + "='" + tripId
					+ "'", null);
		} else {
			id = myDataBase.insert(PARKTRIP, null, values);
		}
		if (id > 0) {
			System.out.println("values inserted.");
		} else {
			System.out.println("values not inserted.");
		}

	}

	private boolean checkExist(String tripId, String userId) {

		boolean status = false;
		Cursor cursor = myDataBase.query(PARKTRIP, null, TRIPID + "='" + tripId
				+ "' and " + USERID + "='" + userId + "'", null, null, null,
				null);
		if (cursor != null) {
			if (cursor.getCount() > 0)
				status = true;
			cursor.close();
		}
		return status;
	}

	public Cursor fetchParkFees(String id) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + PARKTRIP + " where tripId='"
				+ id + "'", null);
		if (c != null)
			c.moveToFirst();
		return c;
	}

	public Cursor fetchERPLogbyTripId(String id) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + TRIPERPLOG
				+ " where tripId='" + id + "'", null);
		if (c != null)
			c.moveToFirst();
		return c;
	}

	public Cursor fetchAllTrips() {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT *  FROM " + TRIPS + " ORDER BY "
				+ STARTTIME + " DESC", null);
		if (c != null)
			c.moveToFirst();
		return c;
	}

	public Cursor deleteSingleTripByid(String id) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("DELETE FROM " + TRIPS + " where tripId=" + id,
				null);
		if (c != null)
			c.moveToFirst();
		return c;
	}

}