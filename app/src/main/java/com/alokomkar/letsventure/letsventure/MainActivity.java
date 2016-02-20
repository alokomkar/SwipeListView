package com.alokomkar.letsventure.letsventure;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;


import com.alokomkar.letsventure.letsventure.view.FlingCardListener;
import com.alokomkar.letsventure.letsventure.view.OnActionClickInterface;
import com.alokomkar.letsventure.letsventure.view.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements FlingCardListener.ActionDownInterface {

    private MyAppAdapter myAppAdapter;
    private ViewHolder viewHolder;
    private ArrayList<Integer> dataDrawableColorsList;
    private SwipeFlingAdapterView flingContainer;
    private OnActionClickInterface onActionClickInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        onActionClickInterface = new OnActionClickInterface() {
            @Override
            public void performAction() {
                removeBackground();
            }
        };

        dataDrawableColorsList = new ArrayList<>();
        dataDrawableColorsList.add(android.R.color.holo_blue_light);
        dataDrawableColorsList.add(android.R.color.holo_blue_dark);
        dataDrawableColorsList.add(android.R.color.holo_blue_light);
        dataDrawableColorsList.add(android.R.color.holo_blue_dark);
        dataDrawableColorsList.add(android.R.color.holo_blue_light);
        dataDrawableColorsList.add(android.R.color.holo_blue_dark);

        myAppAdapter = new MyAppAdapter(dataDrawableColorsList, MainActivity.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setOnActionClickListener( onActionClickInterface );
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                dataDrawableColorsList.remove(0);
                myAppAdapter.notifyDataSetChanged();
                showToast("Like!!!");
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

            }

            @Override
            public void onRightCardExit(Object dataObject) {

                dataDrawableColorsList.remove(0);
                myAppAdapter.notifyDataSetChanged();
                showToast("Unlike :(");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                //view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                //view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);

                myAppAdapter.notifyDataSetChanged();
            }
        });

    }

    private void removeBackground() {
        viewHolder.background.setVisibility(View.GONE);
        myAppAdapter.notifyDataSetChanged();
    }

    private void showToast(String message) {
        Toast.makeText( MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActionDownPerform() {
        Log.e("action", "bingo");
    }

    public static class ViewHolder {
        public static FrameLayout background;
        public ImageView cardImage;
    }

    public class MyAppAdapter extends BaseAdapter {


        public List<Integer> integerList;
        public Context context;

        private MyAppAdapter(List<Integer> apps, Context context) {
            this.integerList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return integerList.size();
        }

        @Override
        public Integer getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.cardImage.setImageDrawable(ContextCompat.getDrawable(context, integerList.get(position)));

            return rowView;
        }
    }


}