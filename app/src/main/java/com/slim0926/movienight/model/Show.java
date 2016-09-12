package com.slim0926.movienight.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sue on 8/30/16.
 */

public class Show implements Parcelable {

    private String mTitle;
    private String mOverview;

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Show() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mOverview);
    }

    private Show(Parcel in) {
        mTitle = in.readString();
        mOverview = in.readString();
    }

    public static final Creator<Show> CREATOR = new Creator<Show>() {
        @Override
        public Show createFromParcel(Parcel parcel) {
            return new Show(parcel);
        }

        @Override
        public Show[] newArray(int i) {
            return new Show[i];
        }
    };


}
