package com.example.stpl.workloc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	public static DbHelper sIntance;

	public DbHelper(Context context) {
		super(context, Entity.DATABASE_NAME, null, Entity.DATABASE_VERSION);

	}

	public static DbHelper getIntence(Context context) {
		if (sIntance == null) {
			sIntance = new DbHelper(context);
		}
		return sIntance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE " + Entity.LIVETRIPTABLE + " ("
				+ Entity.TRIPID + " INTEGER , " + Entity.LAT + " TEXT , "
				+ Entity.LOCATION + " TEXT , " + Entity.LON + " TEXT , "
				+ Entity.USERID + " TEXT , " + Entity.SPEED + " TEXT , "
				+ Entity.TIME + " TEXT , " + Entity.DISTANCE + " TEXT )");

		db.execSQL("CREATE TABLE " + Entity.TRIPLOG + " (" + Entity.USERID
				+ " TEXT , " + Entity.TRIPID + " TEXT , " + Entity.TIME
				+ " TEXT , " + Entity.SPEED + " TEXT , " + Entity.LON
				+ " TEXT , " + Entity.LOCATION + " TEXT , " + Entity.LAT
				+ " TEXT , " + Entity.DISTANCE + " TEXT )");

		db.execSQL("CREATE TABLE " + Entity.PARKTRIP + " (" + Entity.USERID
				+ " TEXT , " + Entity.TRIPID + " TEXT , " + Entity.TIME
				+ " TEXT , " + Entity.PAYMODE + " TEXT , " + Entity.LOCATION
				+ " TEXT , " + Entity.INFO + " TEXT , " + Entity.FEE
				+ " TEXT ) ");

		db.execSQL("CREATE TABLE " + Entity.TRIPS + " (" + Entity.USERID
				+ " TEXT , " + Entity.TRIPID + " TEXT , " + Entity.STARTTIME
				+ " TEXT , " + Entity.DISTANCECLAIMED + " TEXT , "
				+ Entity.DISTANCECOVERED + " TEXT , "
				+ Entity.DISTANCECOVEREDBYAPP + " TEXT , "
				+ Entity.REIMBURSEMENTRATE + " TEXT , " + Entity.TRIPCOMMENT
				+ " TEXT , " + Entity.TRIPPURPOSE + " TEXT , "
				+ Entity.TRIPTYPE + " TEXT , " + Entity.VEHICLENUMBER
				+ " TEXT , " + Entity.ENDTIME + " TEXT , " + Entity.ISEXPORTED
				+ " TEXT , " + Entity.NOTE + " TEXT , " + Entity.TOTALAMOUNT
				+ " TEXT , " + Entity.ODOMETEREND + " TEXT , "
				+ Entity.ODOMETERSTART + " TEXT , " + Entity.STARTEDFROM
				+ " TEXT , " + Entity.STARTLOCATION + " TEXT , "
				+ Entity.DRIVERNAME + " TEXT , " + Entity.MILEAGECLAIMED
				+ " TEXT , " + Entity.HTOD + " TEXT , " + Entity.ENDLOCATION
				+ " TEXT ) ");

		db.execSQL("CREATE TABLE " + Entity.ERP_LOCATIONS + " ("
				+ Entity.SERVER_ERP_ID + " TEXT , " + Entity.ZONE_ID
				+ " TEXT , " + Entity.GANTRY_ID + " TEXT , "
				+ Entity.GANTRY_NAME + " TEXT , " + Entity.COSLAT + " TEXT , "
				+ Entity.SINLAT + " TEXT , " + Entity.COSLNG + " TEXT , "
				+ Entity.SINLNG + " TEXT , " + Entity.LONGITUDE + " TEXT , "
				+ Entity.LATITUDE + " TEXT ) ");

		db.execSQL("CREATE TABLE " + Entity.ERP_FEES + " ("
				+ Entity.SERVER_ERP_FEE_ID + " TEXT , " + Entity.VCC_TYPE
				+ " TEXT , " + Entity.VCC_TYPE_ID + " TEXT , "
				+ Entity.DAY_TYPE + " TEXT , " + Entity.ENDTIME + " TEXT , "
				+ Entity.STARTTIME + " TEXT , " + Entity.ZONE_ID + " TEXT , "
				+ Entity.ERP_FEE + " TEXT ) ");

		db.execSQL("CREATE TABLE " + Entity.TRIPERPLOG + " (" + Entity.DATE
				+ " TEXT , " + Entity.ERP_FEE + " TEXT , " + Entity.ERP_LAT
				+ " TEXT , " + Entity.ERP_LONG + " TEXT , "
				+ Entity.ERP_ROADNAME + " TEXT, " + Entity.ERP_TIME
				+ " TEXT , " + Entity.ERPGANTRY_ID + " TEXT , "
				+ Entity.ERPGANTRYUNIQUEID + " TEXT , " + Entity.TIMESTAMP
				+ " TEXT , " + Entity.TRIPID + " TEXT , " + Entity.USERID
				+ " TEXT )");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + Entity.LIVETRIPTABLE);
		db.execSQL("DROP TABLE IF EXISTS " + Entity.TRIPLOG);
		db.execSQL("DROP TABLE IF EXISTS " + Entity.PARKTRIP);
		db.execSQL("DROP TABLE IF EXISTS " + Entity.TRIPERPLOG);
		
		onCreate(db);
	}

}
