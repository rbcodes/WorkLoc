package com.example.stpl.workloc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;

public class TripList extends AppCompatActivity {

    SQLiteHandler db;
    SessionManager session;
    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());
        user = session.getUserDetails();

        Toast.makeText(TripList.this, user.get(SessionManager.KEY_EMAIL), Toast.LENGTH_SHORT).show();
        Toast.makeText(TripList.this,  db.getid(user.get(SessionManager.KEY_EMAIL)), Toast.LENGTH_SHORT).show();
    }

}
