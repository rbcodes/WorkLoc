package com.example.stpl.workloc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class LogDetailActivity extends Activity implements OnClickListener {
	private TextView TripName, StrDateTime, EndDateTime, StrAddress,
			EndAddress, DisTravelled, DisClaimed, MlgClaimed, ParkLocation,
			ParkFee, PaymentMode, ParkTime;
	private Button commentBtn, uploadBtn, emailBtn, adderp;
	private ImageView EditPrkingInfo, backbtn1, mapIcon;
	private LinearLayout addErp, parkin, comment_layout, addComment;
	private Entity entity;
	private String id;
	TextView comment;
	char i = 0;

	ListView erp_list_view;
	String filepath = "";
	public boolean email = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.log_detail);
		id = getIntent().getStringExtra("TRIPID");
		entity = new Entity(LogDetailActivity.this);
		entity.open();

	}

	private void setVAlue() {
		Cursor c = entity.fetchSingleTripByid(id);

		if (c != null) {
			while (c.isAfterLast() == false) {

				TripName.setText(c.getString(c
						.getColumnIndex(Entity.TRIPPURPOSE)));
				StrDateTime.setText(c.getString(c
						.getColumnIndex(Entity.STARTTIME)));
				StrAddress.setText(c.getString(c
						.getColumnIndex(Entity.STARTLOCATION)));
				EndAddress.setText(c.getString(c
						.getColumnIndex(Entity.ENDLOCATION)));
				
				NumberFormat myFormat = new DecimalFormat("#,##0.0#");
				Float myNumber = Float.parseFloat(c.getString(c
						.getColumnIndex(Entity.DISTANCECOVEREDBYAPP)));
				String myValue = myFormat.format(myNumber);
				EndDateTime.setText(c.getString(c
						.getColumnIndex(Entity.ENDTIME)));
				DisTravelled.setText(myValue);
				try {
//					double dist = Math.round(Integer.parseInt(c.getString(c
//							.getColumnIndex(Entity.DISTANCECLAIMED))) * 100) / 100;
					
					double dist = Double.parseDouble(c.getString(c
							.getColumnIndex(Entity.DISTANCECLAIMED)));
					
					
					DisClaimed.setText(myFormat.format(dist));
				} catch (Exception e) {
					e.printStackTrace();
				}
				MlgClaimed.setText("$"+c.getString(c
						.getColumnIndex(Entity.TOTALAMOUNT)));
				if (!c.getString(c.getColumnIndex(Entity.TRIPCOMMENT)).equals(
						"")) {
					comment_layout.setVisibility(View.VISIBLE);
					comment.setText(c.getString(c
							.getColumnIndex(Entity.TRIPCOMMENT)));
				} else {
					comment_layout.setVisibility(View.GONE);
				}
				c.moveToNext();
			}
		}

		try {
			Cursor cc = entity.fetchParkFees(id);
			System.out.println("id   " + id);
			if (cc != null) {
				ParkTime.setText(cc.getString(cc.getColumnIndex(Entity.TIME)));
				if (!cc.getString(cc.getColumnIndex(Entity.FEE)).equals("")) {
					ParkFee.setText(cc.getString(cc.getColumnIndex(Entity.FEE)));
				} else {
					ParkFee.setText("0");
				}

				ParkLocation.setText(cc.getString(cc
						.getColumnIndex(Entity.LOCATION)));
				PaymentMode.setText(cc.getString(cc
						.getColumnIndex(Entity.PAYMODE)));

				c.moveToNext();
				System.out.println("details" + "   "
						+ cc.getString(cc.getColumnIndex(Entity.TIME)) + "   "
						+ cc.getString(cc.getColumnIndex(Entity.FEE)) + "   "
						+ cc.getString(cc.getColumnIndex(Entity.LOCATION)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	@Override
	protected void onResume() {
		super.onResume();
		setVAlue();
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.adjust_height:
			Cursor ar = entity.fetchExtraid(Integer.parseInt(id));
			ArrayList<LatLongData> geopoint = new ArrayList<LatLongData>();

			if (ar != null) {
				ar.moveToFirst();
				while (ar.isAfterLast() == false) {

					LatLongData parce = new LatLongData(ar.getString(3),
							ar.getString(1));
					geopoint.add(parce);
					ar.moveToNext();
				}
			}
			if (geopoint.size() > 0) {
				Intent tt = new Intent(LogDetailActivity.this, All.class);
				tt.putParcelableArrayListExtra("key", geopoint);
				startActivity(tt);
			} else {
				Toast.makeText(LogDetailActivity.this,
						"Locations are not saved.", Toast.LENGTH_LONG).show();
			}
			break;


		default:
			break;
		}
	}



	private void generateCsvFile() throws IOException {
		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/test/CSV");
		boolean var = false;
		if (!folder.exists()) {
			var = folder.mkdirs();
		}
		System.out.println("" + var);
		if (email) {

			SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd");
			filepath = folder.toString() + "/"
					+ dateFormatGmt.format(new Date()) + "_Trip_Record"
					+ ".csv";
			email = false;
		} else {
			filepath = folder.toString() + "/" + id + ".csv";
		}
		// show waiting screen
		// final ProgressDialog progDailog = ProgressDialog.show(
		// LogDetailActivity.this, "CSV FILE CREATED",
		// "even geduld aub...", true);// please wait
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
			}
		};
		new Thread() {
			public void run() {
				try {
					entity.open();
					FileWriter writer = new FileWriter(filepath);
					Cursor cursor = entity.fetchSingleTripByid(id);
					writer.append("Driver Name" + ',');
					writer.append("Vehicle Number" + ',');
					writer.append("Trip Type" + ',');
					writer.append("Trip Purpose" + ',');
					writer.append("Started From" + ',');
					writer.append("Start Time" + ',');
					writer.append("Start Location" + ',');
					writer.append("End Time" + ',');
					writer.append("End Location" + ',');
					writer.append("Start Odometer Reading" + ',');
					writer.append("End Odometer Reading" + ',');
					writer.append("Distance Covered" + ',');
					writer.append("Distance Covered By App" + ',');
					writer.append("Distance Claimed" + ',');
					writer.append("Mileage Claimed" + ',');
					writer.append("Total Claimed Amount" + ',');
					writer.append("Trip Comment");
					writer.append('\n');
					if (cursor.moveToFirst()) {
						do {
							System.out.println("csvcsvcsvcsv"
									+ cursor.getString(0).equals(" "));
							if (!cursor.getString(0).toString().equals(" ")
									&& !cursor.getString(1).toString()
											.equals(" ")
									&& !cursor.getString(2).toString()
											.equals(" ")) {
								writer.append(cursor.getString(cursor
										.getColumnIndex(Entity.DRIVERNAME)) + ',');
								writer.append(cursor.getString(cursor
										.getColumnIndex(Entity.VEHICLENUMBER)) + ',');
								writer.append(cursor.getString(cursor
										.getColumnIndex(Entity.TRIPTYPE)) + ',');
								writer.append(cursor.getString(cursor
										.getColumnIndex(Entity.TRIPPURPOSE)) + ',');
								writer.append(cursor.getString(cursor
										.getColumnIndex(Entity.STARTEDFROM)) + ',');
								writer.append(cursor.getString(cursor
										.getColumnIndex(Entity.STARTTIME)) + ',');
								writer.append(cursor
										.getString(
												cursor.getColumnIndex(Entity.STARTLOCATION))
										.replace(",", "*") + ',');
								writer.append(cursor.getString(cursor
										.getColumnIndex(Entity.ENDTIME)) + ',');
								writer.append(cursor
										.getString(
												cursor.getColumnIndex(Entity.ENDLOCATION))
										.replace(",", "*") + ',');
								writer.append(cursor.getString(cursor
										.getColumnIndex(Entity.ODOMETERSTART)) + ',');
								writer.append(cursor.getString(cursor
										.getColumnIndex(Entity.ODOMETEREND)) + ',');
								writer.append(cursor.getString(cursor
										.getColumnIndex(Entity.DISTANCECOVERED)) + ',');
								writer.append(cursor.getString(cursor
										.getColumnIndex(Entity.DISTANCECOVEREDBYAPP)) + ',');
								writer.append(cursor.getString(cursor
										.getColumnIndex(Entity.DISTANCECLAIMED)) + ',');
								writer.append(cursor.getString(cursor
										.getColumnIndex(Entity.MILEAGECLAIMED)) + ',');
								writer.append(cursor.getString(cursor
										.getColumnIndex(Entity.TOTALAMOUNT)) + ',');
								writer.append(cursor.getString(cursor
										.getColumnIndex(Entity.TRIPCOMMENT)));
								writer.append('\n');
							}
						} while (cursor.moveToNext());
					}
					// writer.append("Latitude" + ',');
					// writer.append("Longitude" + ',');
					// writer.append("Speed" + ',');
					// writer.append('\n');
					if (cursor != null && !cursor.isClosed()) {
						cursor.close();
					}
					writer.flush();
					writer.close();
				} catch (Exception e) {
				}
				handler.sendEmptyMessage(0);
				// progDailog.dismiss();
				// entity.close();
			}
		}.start();
	}

	private class ViewHolder {
		TextView logname;
		RelativeLayout centerlayout;
	}





}