package com.slim0926.movienight.ui;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.slim0926.movienight.R;
import com.slim0926.movienight.adapters.ShowAdapter;
import com.slim0926.movienight.model.Show;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by sue on 8/30/16.
 */

public class ShowResultsActivity extends AppCompatActivity {

    private Show[] mShows;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.SHOW_SEARCH);
        mShows = Arrays.copyOf(parcelables, parcelables.length, Show[].class);

        ShowAdapter adapter = new ShowAdapter(this, mShows);
        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

    }
}
