package com.example.stpl.workloc;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by niccapdevila on 3/26/16.
 */
public class FoodFragment extends BaseFragment {

    ListView listView;
    ArrayList<String> ids  = new ArrayList<String>();
    ArrayList<String> triptimestamps  = new ArrayList<String>();
    ArrayList<HashMap<String ,String>> attendences = new ArrayList<HashMap<String ,String>>();
    ArrayList<HashMap<String ,String>> latlong = new ArrayList<HashMap<String ,String>>();
    private SessionManager session;
    private SQLiteHandler db;
    HashMap<String, String> user;
    ArrayList<LatLongData> geopoint = new ArrayList<LatLongData>();

    public static FoodFragment  newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        FoodFragment fragment = new FoodFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        db = new SQLiteHandler(getContext());
        session = new SessionManager(getContext());
        user = session.getUserDetails();

        listView = (ListView)view.findViewById(R.id.listview);
        attendences = db.gettripslog(user.get(SessionManager.KEY_ID));



        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, triptimestamps);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        triptimestamps.clear();
        ids.clear();
        attendences = db.gettripslog(user.get(SessionManager.KEY_ID));

        for(int d = 0; d<attendences.size();d++) {
            HashMap<String, String> classname;
            classname = attendences.get(d);
            ids.add(classname.get("id"));
            triptimestamps.add(classname.get("time"));
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, triptimestamps);


        // Assign adapter to ListView
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                latlong = db.getlivetripslog(ids.get(position));

                for(int d = 0; d<latlong.size();d++) {
                    HashMap<String, String> classname;
                    classname = latlong.get(d);
                    LatLongData parce = new LatLongData(classname.get("lat"),classname.get("lon"));
                    geopoint.add(parce);
                }

                if (geopoint.size() > 0) {
                    Intent tt = new Intent(getContext(), All.class);
                    tt.putParcelableArrayListExtra("key", geopoint);
                    startActivity(tt);
                } else {
                    Toast.makeText(getActivity(),
                            "Locations are not saved.", Toast.LENGTH_LONG).show();
                }
                int itemPosition = position;
                String itemValue = (String) listView.getItemAtPosition(position);
                Toast.makeText(getActivity(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();


            }

        });
    }

}
