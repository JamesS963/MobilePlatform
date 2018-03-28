package com.example.james.coursework17;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by James on 24/03/2018.
 * s1631711
 */

public class PlannedRoadworksAdapter extends ArrayAdapter <String> {


    private Context mContext;
    private int id;
    private List<String> items;

    public PlannedRoadworksAdapter(Context context, int textViewResourceId, List<String> list) {
        super(context, textViewResourceId, list);
        mContext = context;
        id = textViewResourceId;
        items = list;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        View mView = v;
        if (mView == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, null);
        }

        TextView text = (TextView) mView.findViewById(android.R.id.text1);

        if (position!=0) {
            //checks if the position isn't null
            if (items.get(position) != null) {
                //gets the string
                String item = items.get(position);
                //set up date format
                DateFormat format = new SimpleDateFormat("EEEE, dd MMMM yyyy - HH:mm");



                try {
                    //format date correctly
                    String sd = item.substring(item.lastIndexOf("Start Date: ") + 12, item.length());
                    String[] sdparts = sd.split("\n");
                    Date sdDate = format.parse(sdparts[0]);

                    //format date correctly
                    String ed = item.substring(item.lastIndexOf("End Date: ") + 10);
                    String[] edparts = ed.split("\n");
                    Date edDate = format.parse(edparts[0]);

                    System.out.println("===================================");
                    System.out.println("first date : " + sdparts[0]);
                    System.out.println("first time = " + sdDate.getTime());
                    System.out.println(" ");
                    System.out.println("end date: " + edparts[0]);
                    System.out.println("end time =: " + edDate.getTime());
                    System.out.println(" ");
                    System.out.println("differance is " +(edDate.getTime() - sdDate.getTime())/(1000*60*60*24));
                    System.out.println("===================================");

                    //get's total days between dates
                    long differance = (edDate.getTime() - sdDate.getTime())/(1000*60*60*24);
                    //sets up the colour of lenght of time between dates from green to red
                    if (differance == 0) {
                        text.setTextColor(Color.BLACK);
                        text.setText(items.get(position));
                        text.setBackgroundColor(Color.GREEN);
                        //int color = Color.argb(200, 255, 64, 64);
                        //text.setBackgroundColor(color);
                    }

                    if(differance <30 && differance>0) {
                        text.setTextColor(Color.BLACK);
                        text.setText(items.get(position));
                        text.setBackgroundColor(Color.YELLOW);
                        //int color = Color.argb(12, 33, 233, 122);
                       // text.setBackgroundColor(color);
                    }
                    if(differance >= 30){
                        text.setTextColor(Color.BLACK);
                        text.setText(items.get(position));
                        text.setBackgroundColor(Color.RED);
                    }
                }catch(Exception e) {System.out.println("date parsed incorrecttly"); }

            }
        }

        return mView;

    }
}
