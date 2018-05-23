package com.example.stpl.workloc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = "SQLiteHandler";

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "naiveindia.workloc";

    // Login table name
    private static final String TABLE_USERS = "users";
    private static final String TABLE_TRIP_LOG = "triplog";
    private static final String TABLE_LIVE_TRIP_LOG = "livetriplog";


    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_USERID = "user_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("HHHHHHHHHHHHHHHHHH", "in on create");

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_NAME + " TEXT," +KEY_IMAGE +" TEXT,"+ KEY_EMAIL + " TEXT,"+ KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_TRIP_LOG_TABLE = "CREATE TABLE " + TABLE_TRIP_LOG+ "( id INTEGER PRIMARY KEY AUTOINCREMENT,user_id TEXT ,"
                + "time TEXT )";
        db.execSQL(CREATE_TRIP_LOG_TABLE);

        String CREATE_LIVE_TRIP_LOG_TABLE = "CREATE TABLE " + TABLE_LIVE_TRIP_LOG+"( id INTEGER PRIMARY KEY AUTOINCREMENT, trip_id TEXT ,"
                + "lat TEXT,lon TEXT, speed TEXT, distance TEXT, time TEXT)";
        db.execSQL(CREATE_LIVE_TRIP_LOG_TABLE);

        Log.d("HHHHHHHHHHHHHHHHHH", "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIVE_TRIP_LOG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP_LOG);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name,String image, String email,String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues(); // user_id
        values.put(KEY_NAME, name); // Name
        values.put(KEY_IMAGE, image); // user_image
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        long id = db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection

        Log.d("HHHHHHHHHHHHHH", "New user inserted into sqlite: " + id);
    }

    /**
     * Storing class details in database
     * */





    public void addlivetriplog(String tripid,String LAT,String LON, String SPEED,String DISTANCE, String TIME) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("trip_id", tripid); // user_id
        values.put("lat", LAT); // Name
        values.put("lon", LON); // user_image
        values.put("speed", SPEED); // Email
        values.put("distance", DISTANCE); // Email
        values.put("time", TIME); // Created At

        // Inserting Row
        long id = db.insert(TABLE_LIVE_TRIP_LOG, null, values);
        db.close(); // Closing database connection

        Log.d("HHHHHHHHHHHHH", "New trip inserted into sqlite: " + tripid+" "+ LAT+ " "+LON+" "+DISTANCE+" "+TIME);
    }


    public void addtriplog(String userid,String time) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user_id", userid); // user_id
        values.put("time", time); // Name

        // Inserting Row
        long id = db.insert(TABLE_TRIP_LOG, null, values);
        db.close(); // Closing database connection

        Log.d("HHHHHHHHH", "New maintrip inserted into sqlite: " + id + " "+ userid+ " "+time);
    }


    public ArrayList<HashMap<String, String>> gettripslog(String userid) {
        ArrayList<HashMap<String, String>> classes = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM " + TABLE_TRIP_LOG ;

        Log.d("HHHHHHHHHHH", "SELECT QUERY: " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                HashMap<String, String> user = new HashMap<String, String>();

                user.put("id", cursor.getString(cursor.getColumnIndex("id")));
                user.put("time", cursor.getString(cursor.getColumnIndex("time")));
                user.put("user_id", cursor.getString(cursor.getColumnIndex("user_id")));

                Log.d("HHHHHHHHHHH", "Fetching user from Sqlite: " + user.toString() + " " + cursor.getCount());
                classes.add(user);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user
        return classes;
    }

    String CREATE_LIVE_TRIP_LOG_TABLE = "CREATE TABLE " + TABLE_LIVE_TRIP_LOG+"( id INTEGER PRIMARY KEY AUTOINCREMENT, trip_id TEXT ,"
            + "lat TEXT,lon TEXT, speed TEXT, distance TEXT, time TEXT)";


    public ArrayList<HashMap<String, String>> getlivetripslog(String tripid) {
        ArrayList<HashMap<String, String>> classes = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + TABLE_LIVE_TRIP_LOG + " where trip_id=" + tripid + " ORDER BY time DESC";

        Log.d("HHHHHHHHHHH", "SELECT QUERY: "+selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                HashMap<String, String> user = new HashMap<String, String>();

                user.put("id", cursor.getString(cursor.getColumnIndex("id")));
                user.put("lat", cursor.getString(cursor.getColumnIndex("lat")));
                user.put("lon", cursor.getString(cursor.getColumnIndex("lon")));
                user.put("speed", cursor.getString(cursor.getColumnIndex("speed")));
                user.put("distance", cursor.getString(cursor.getColumnIndex("distance")));
                user.put("time", cursor.getString(cursor.getColumnIndex("time")));

                Log.d("HHHHHHHHHHH", "Fetching user from Sqlite: " + user.toString() + " " + cursor.getCount());
                classes.add(user);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user
        return classes;
    }


    public String gettripid(String timestamp) {
        String userid = "welcome";
        String selectQuery = "SELECT  id FROM " + TABLE_TRIP_LOG +" where time = '"+ timestamp+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                userid = cursor.getString(cursor.getColumnIndex("id"));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user
        return userid;
    }

    public String getid(String mail) {
        String userid = "welcome";
        String selectQuery = "SELECT  id FROM " + TABLE_USERS +" where email = '"+ mail+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                userid = cursor.getString(cursor.getColumnIndex("id"));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user
        return userid;
    }



    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USERS, null, null);
        db.delete(TABLE_LIVE_TRIP_LOG, null, null);
        db.delete(TABLE_TRIP_LOG, null, null);
        db.close();
        Log.d(TAG, "Deleted all user info from sqlite");
    }


}