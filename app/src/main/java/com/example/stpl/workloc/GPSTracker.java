package com.example.stpl.workloc;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.Calendar;
import java.util.HashMap;


public class GPSTracker implements LocationListener  {

    private final Context mContext;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    Location location;
    double latitude;
    double longitude;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;   //25; // 25 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 100;//  //1000 * 60 * 10; // 10 minute
    // Declaring a Location Manager
    protected LocationManager locationManager;
    private IGetActivity activity;
    private static final int PERMISSION_REQUEST_CODE = 101;
    public static final int REQUEST_CHECK_SETTINGS = 0x1;

    SessionManager session;
    private SQLiteHandler db;
    HashMap<String, String> user;
    Boolean runningforfirsttime = true;
    Location startlocation;
    Location endlocation;
    String currenttripid;

    public GPSTracker(Context ctx, IGetActivity act) {
        this.mContext = ctx;
        this.activity = act;


        session = new SessionManager(mContext);
        db = new SQLiteHandler(mContext);

        /*if (checkPermission()) {
            startTracker();
        } else {
            requestPermission();
        }*/
    }

    public void startTracker() {
        if (checkPermission()) {
            if (isProviderAvailable()) {
                getLocation();
            } else {
                settingsrequest();
            }
        } else {
            requestPermission();
        }
    }

    public Location getLocation() {
        try {
            // First get location from GPS Provider
            if (checkPermission()) {

                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        Log.d("GPS Enabled", "GPS manager Enabled");
                        if (location != null) {
                            //latitude = location.getLatitude();
                            //longitude = location.getLongitude();
                            Log.d("FROM GPS", "Tracked: Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude());
                        }
                    }
                }
                if (location == null) {
                    if (isNetworkEnabled) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("Network", "Network Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            Log.d("Network", "Network Manager Enabled");
                            if (location != null) {
                                //latitude = location.getLatitude();
                                //longitude = location.getLongitude();
                                Log.d("FROM Network", "Tracked: Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude());
                            }
                        }
                    }
                }
            }
            // if GPS Enabled get lat/long using GPS Services

        } catch (Exception e) {
            Log.v("Tracker", "Error in getting locaton");
            e.printStackTrace();
        }
        return location;
    }

    public boolean isProviderAvailable() {
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        // getting GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        Log.v("isGPSEnabled", "=" + isGPSEnabled);
        //Toast.makeText(mContext, "GPS Enabled: " + isGPSEnabled, Toast.LENGTH_SHORT).show();
        // getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        //Toast.makeText(mContext, "NETWORK Enabled: " + isNetworkEnabled, Toast.LENGTH_SHORT).show();
        Log.v("isNetworkEnabled", "=" + isNetworkEnabled);
        if (isGPSEnabled == true) { // && isNetworkEnabled == true) {
            canGetLocation = true;
        } else {
            // no network provider is enabled
            canGetLocation = false;
        }
        return canGetLocation;
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    /**
     * Function to get longitude
     */

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    @Override
    public void onLocationChanged(Location loc) {
        if (loc == null)
            return;
                    location = loc;

        user = session.getUserDetails();
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        if(runningforfirsttime)
        {
            startlocation = loc;
            db.addtriplog(user.get(SessionManager.KEY_ID),currentTimestamp+"");
            currenttripid = db.gettripid(currentTimestamp+"");
            runningforfirsttime = false;
        }
        endlocation = loc;
//        entity.storeTripLog();*/


        db.addlivetriplog(currenttripid, loc.getLatitude()+"", loc.getLongitude()+"", loc.getSpeed()+"", loc.distanceTo(startlocation)+"", currentTimestamp+"");
        Toast.makeText(mContext,""+loc,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    /**
     * Function to check if best network provider
     *
     * @return boolean
     */

    public boolean canGetLocation() {
        return this.canGetLocation;
    }


    public void settingsrequest() {
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(activity.getGpsActivity())
                .addApi(LocationServices.API).build();
        //.addConnectionCallbacks(this)
        //.addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(activity.getGpsActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    /**
     * Function to show settings alert dialog
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS Settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */

    public void stopUsingGPS() {
        if (locationManager != null) {
            if (checkPermission())
                locationManager.removeUpdates(GPSTracker.this);
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(activity.getGpsActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
    }
}
