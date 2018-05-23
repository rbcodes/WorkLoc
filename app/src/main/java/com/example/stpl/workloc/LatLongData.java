package com.example.stpl.workloc;

import android.os.Parcel;
import android.os.Parcelable;

public class LatLongData implements Parcelable {
	public String latitude;
	public String longitude;

	public LatLongData(Parcel in) {
		latitude = in.readString();
		longitude = in.readString();
	}

	public LatLongData(String lat, String longi) {
		latitude = lat;
		longitude = longi;
	}

	@Override
	public int describeContents() {

		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(latitude);
		dest.writeString(longitude);

	}

	public static final Creator<LatLongData> CREATOR = new Creator<LatLongData>() {

		@Override
		public LatLongData[] newArray(int size) {
			// TODO Auto-generated method stub
			return new LatLongData[size];
		}

		@Override
		public LatLongData createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new LatLongData(source);
		}
	};

}
