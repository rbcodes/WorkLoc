package com.example.stpl.workloc;


import java.util.ArrayList;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class All extends FragmentActivity {
    ArrayList<LatLongData> polyz = new ArrayList<LatLongData>();
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all);
        Bundle oo = getIntent().getExtras();
        polyz = oo.getParcelableArrayList("key");
        map = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        new Asy().execute();

    }

    private class Asy extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            for (int i = 0; i < polyz.size() - 1; i++) {
//				LatLng src = new LatLng(
//						Double.parseDouble(((LatLongData) polyz.get(i)).latitude),
//						Double.parseDouble(((LatLongData) polyz.get(i)).longitude));
//				LatLng dest = new LatLng(
//						Double.parseDouble(((LatLongData) polyz.get(i + 1)).latitude),
//						Double.parseDouble(((LatLongData) polyz.get(i + 1)).longitude));
//				map.addPolyline(new PolylineOptions()
//						.add(new LatLng(src.latitude, src.longitude),
//								new LatLng(dest.latitude, dest.longitude))
//						.width(3).color(Color.RED).geodesic(true));


                map.addPolyline(new PolylineOptions()
                        .add(new LatLng(Double.parseDouble(((LatLongData) polyz.get(i)).latitude), Double.parseDouble(((LatLongData) polyz.get(i)).longitude)),
                                new LatLng(Double.parseDouble(((LatLongData) polyz.get(i + 1)).latitude), Double.parseDouble(((LatLongData) polyz.get(i + 1)).longitude)))
                        .width(3).color(Color.RED).geodesic(true));

            }

            if (polyz.size() > 0) {
                MarkerOptions sourcepoint = new MarkerOptions();
                // Setting latitude and longitude for the marker
                sourcepoint
                        .position(new LatLng(
                                Double.parseDouble(((LatLongData) polyz.get(0)).latitude),
                                Double.parseDouble(((LatLongData) polyz.get(0)).longitude)));
                map.addMarker(sourcepoint);
            }
            if ((polyz.size()) > 1) {
                MarkerOptions destination = new MarkerOptions();
                destination.position(new LatLng(
                        Double.parseDouble(((LatLongData) polyz.get(polyz
                                .size() - 1)).latitude), Double
                        .parseDouble(((LatLongData) polyz.get(polyz
                                .size() - 1)).longitude)));
                map.addMarker(destination);
            }
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(Double.parseDouble(((LatLongData) polyz
                            .get(polyz.size() / 2)).latitude),
                            Double.parseDouble(((LatLongData) polyz.get(polyz
                                    .size() / 2)).longitude)), 18));

        }

    }
}
