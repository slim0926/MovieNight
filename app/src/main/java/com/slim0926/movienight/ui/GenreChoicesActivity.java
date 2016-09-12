package com.slim0926.movienight.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.slim0926.movienight.R;
import com.slim0926.movienight.adapters.GenreAdapter;
import com.slim0926.movienight.model.Genre;

import java.util.Arrays;

/**
 * Created by sue on 9/1/16.
 */

public class GenreChoicesActivity extends ListActivity {

    private Genre[] mGenres;
    private int[] mColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.GENRE_CHOICES);
        mGenres = Arrays.copyOf(parcelables, parcelables.length, Genre[].class);

        GenreAdapter adapter = new GenreAdapter(this, mGenres);
        setListAdapter(adapter);

        mColors = new int[mGenres.length];
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        int startColor = v.getDrawingCacheBackgroundColor();


        if (startColor == Color.parseColor("#89a87e")) {
            v.setBackgroundColor(mColors[position]);
            v.setDrawingCacheBackgroundColor(mColors[position]);

        } else {
            v.setBackgroundColor(Color.parseColor("#89a87e"));
            if (mColors[position] == 0) {
                mColors[position] = startColor;
            }
            v.setDrawingCacheBackgroundColor(Color.parseColor("#89a87e"));

        }








    }
}
