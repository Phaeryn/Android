package com.pleb.decisionsandroid;

/**
 * Created by Maxime on 16/04/2017.
 */

import android.content.Context;

import java.util.List;

import com.daimajia.swipe.adapters.ArraySwipeAdapter;


public class ListViewAdapter<T> extends ArraySwipeAdapter{

    public ListViewAdapter(Context context, int resource) {
        super(context, resource);
    }

    public ListViewAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public ListViewAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }

    public ListViewAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public ListViewAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    public ListViewAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}
