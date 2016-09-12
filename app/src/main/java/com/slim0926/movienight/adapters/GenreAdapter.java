package com.slim0926.movienight.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.slim0926.movienight.R;
import com.slim0926.movienight.model.Genre;

/**
 * Created by sue on 9/1/16.
 */

public class GenreAdapter extends BaseAdapter {

    private Context mContext;
    private Genre[] mGenres;

    public GenreAdapter(Context context, Genre[] genres) {
        mContext = context;
        mGenres = genres;
    }

    @Override
    public int getCount() {
        return mGenres.length;
    }

    @Override
    public Object getItem(int position) {
        return mGenres[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.genre_list_item, null);
            holder = new ViewHolder();
            holder.genreLabel = (TextView) convertView.findViewById(R.id.genreLabel);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Genre genre = mGenres[position];

        holder.genreLabel.setText(genre.getGenreName());

        return convertView;
    }

    private static class ViewHolder {
        TextView genreLabel;
    }
}
