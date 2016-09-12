package com.slim0926.movienight.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.slim0926.movienight.R;
import com.slim0926.movienight.model.Filters;
import com.slim0926.movienight.model.FiltersOptions;
import com.slim0926.movienight.model.Genre;
import com.slim0926.movienight.model.Genres;
import com.slim0926.movienight.model.Show;
import com.slim0926.movienight.model.Shows;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.support.v7.appcompat.R.styleable.View;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String SHOW_SEARCH = "SHOW_SEARCH";
    public static final String GENRE_CHOICES = "GENRE_CHOICES";
    public static final String API_KEY = "761ae9f5d432609a0aebe4dd9464aac0";

    private FiltersOptions mFiltersOptions = new FiltersOptions();
    private Shows mShows;
    private Filters mFilters;
    private Genres mGenres;

    @BindView(R.id.movieCheckBox) CheckBox mMovieCheckBox;
    @BindView(R.id.tvCheckBox) CheckBox mTVCheckBox;
    @BindView(R.id.ratingThresholdSpinner) Spinner mRatingThresholdSpinner;
    @BindView(R.id.minRatingNumEditText) EditText mMinRatingsNumEditText;
    @BindView(R.id.maxYearSpinner) Spinner mMaxYearSpinner;
    @BindView(R.id.maxMonthSpinner) Spinner mMaxMonthSpinner;
    @BindView(R.id.maxDaySpinner) Spinner mMaxDaySpinner;
    @BindView(R.id.minYearSpinner) Spinner mMinYearSpinner;
    @BindView(R.id.minMonthSpinner) Spinner mMinMonthSpinner;
    @BindView(R.id.minDaySpinner) Spinner mMinDaySpinner;
    @BindView(R.id.searchButton) Button mSearchButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMovieCheckBox.setChecked(true);
        mSearchButton.setClickable(false);
        mSearchButton.setTextColor(Color.GRAY);
        loadFilterOptions();
        getGenreList();
    }

    private void getGenreList() {
        String genreListUrl = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + API_KEY;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(genreListUrl).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);
                    if (response.isSuccessful()) {
                        mGenres = parseGenreDetails(jsonData);
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Exception caught: ", e);
                } catch (JSONException e) {
                    Log.e(TAG, "Exception caught: ", e);
                }
            }
        });

    }

    private Genres parseGenreDetails(String jsonData) throws JSONException {
        Genres genres = new Genres();

        genres.setGenres(getGenres(jsonData));

        return genres;
    }

    private Genre[] getGenres(String jsonData) throws JSONException {
        JSONObject genres = new JSONObject(jsonData);
        JSONArray data = genres.getJSONArray("genres");

        Genre[] movieGenres = new Genre[data.length()];

        for (int i=0; i<data.length(); i++) {
            JSONObject jsonShow = data.getJSONObject(i);
            Genre movieGenre = new Genre();

            movieGenre.setGenreName(jsonShow.getString("name"));


            movieGenres[i] = movieGenre;
        }

        return movieGenres;
    }

    @OnClick (R.id.genreButton)
    public void startGenreActivity(View view) {
        Intent intent = new Intent(this, GenreChoicesActivity.class);
        intent.putExtra(GENRE_CHOICES, mGenres.getGenres());
        startActivity(intent);
    }

    private void loadFilterOptions() {
        loadSpinner(mRatingThresholdSpinner, mFiltersOptions.getRatingThresholdOptions());
        mMinRatingsNumEditText.setText(FiltersOptions.STARTING_MIN_NUM_OF_RATINGS + "");
        loadSpinner(mMaxMonthSpinner, mFiltersOptions.getReleaseMonths());
        loadSpinner(mMinMonthSpinner, mFiltersOptions.getReleaseMonths());
        loadSpinner(mMaxDaySpinner, mFiltersOptions.getReleaseDays());
        loadSpinner(mMinDaySpinner, mFiltersOptions.getReleaseDays());
        loadSpinner(mMaxYearSpinner, mFiltersOptions.getReleaseYears());
        loadSpinner(mMinYearSpinner, mFiltersOptions.getReleaseYears());


    }

    private void loadSpinner(Spinner spinner, String[] releaseDate) {
        ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, releaseDate);
        spinner.setAdapter(adapter);
    }

    private void loadSpinner(Spinner spinner, ArrayList<String> releaseYear) {
        ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, releaseYear);
        spinner.setAdapter(adapter);
    }

    @OnClick(R.id.setFiltersButton)
    public void getShowInfo() {

        getFilterInput();

        String showInfoUrl = "https://api.themoviedb.org/3/discover/movie?vote_average.gte=" +
                mFilters.getRatingThreshold() + "&vote_count.gte=" +
                mFilters.getMinNumOfRatings() + "&release_date.lte=" +
                mFilters.getMaxDate() + "&release_date.gte=" +
                mFilters.getMinDate() + "&api_key=" + API_KEY;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(showInfoUrl).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);
                    if (response.isSuccessful()) {
                        mShows = parseShowResultsDetails(jsonData);
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Exception caught: ", e);
                } catch (JSONException e) {
                    Log.e(TAG, "Exception caught: ", e);
                }
            }
        });
        mSearchButton.setTextColor(Color.WHITE);
        mSearchButton.setClickable(true);
    }

    private void getFilterInput() {
        mFilters= new Filters();
        String strRatingThreshold = String.valueOf(mRatingThresholdSpinner.getSelectedItem());
        mFilters.setRatingThreshold(Float.parseFloat(strRatingThreshold));

        String strMinRatingsNum = String.valueOf(mMinRatingsNumEditText.getText());
        mFilters.setMinNumOfRatings(Integer.parseInt(strMinRatingsNum));

        String strMaxReleaseDate = mMaxYearSpinner.getSelectedItem() + "-" +
                mMaxMonthSpinner.getSelectedItem() + "-" +
                mMaxDaySpinner.getSelectedItem();


        String strMinReleaseDate = mMinYearSpinner.getSelectedItem() + "-" +
                mMinMonthSpinner.getSelectedItem() + "-" +
                mMinDaySpinner.getSelectedItem();

        mFilters.setMaxDate(strMaxReleaseDate);
        mFilters.setMinDate(strMinReleaseDate);

    }

    private Shows parseShowResultsDetails(String jsonData) throws JSONException {
        Shows showResults = new Shows();

        showResults.setShowResults(getShow(jsonData));

        return showResults;
    }

    private Show[] getShow(String jsonData) throws JSONException {
        JSONObject showResults = new JSONObject(jsonData);
        JSONArray data = showResults.getJSONArray("results");

        Show[] shows = new Show[data.length()];

        for (int i=0; i<data.length(); i++) {
            JSONObject jsonShow = data.getJSONObject(i);
            Show show = new Show();

            show.setTitle(jsonShow.getString("title"));
            show.setOverview(jsonShow.getString("overview"));

            shows[i] = show;
        }

        return shows;
    }

    @OnClick (R.id.searchButton)
    public void startShowActivity() {
        // getShowInfo();

        Intent intent = new Intent(this, ShowResultsActivity.class);
        intent.putExtra(SHOW_SEARCH, mShows.getShowResults());
        startActivity(intent);

        mSearchButton.setClickable(false);
        mSearchButton.setTextColor(Color.GRAY);


    }

}
