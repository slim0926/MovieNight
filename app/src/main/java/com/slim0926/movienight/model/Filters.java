package com.slim0926.movienight.model;

import java.util.Date;

/**
 * Created by sue on 8/2/16.
 */

public class Filters {
    private float mRatingThreshold;
    private int mMinNumOfRatings;
    private String mMinDate;
    private String mMaxDate;

    public float getRatingThreshold() {
        return mRatingThreshold;
    }

    public void setRatingThreshold(float ratingThreshold) {
        mRatingThreshold = ratingThreshold;
    }

    public int getMinNumOfRatings() {
        return mMinNumOfRatings;
    }

    public void setMinNumOfRatings(int minNumOfRatings) {
        mMinNumOfRatings = minNumOfRatings;
    }

    public String getMinDate() {
        return mMinDate;
    }

    public void setMinDate(String minDate) {
        mMinDate = minDate;
    }

    public String getMaxDate() {
        return mMaxDate;
    }

    public void setMaxDate(String maxDate) {
        mMaxDate = maxDate;
    }
}
