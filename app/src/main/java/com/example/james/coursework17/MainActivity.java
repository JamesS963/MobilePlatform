package com.example.james.coursework17;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//burttons for view
    Button Button, Button1;
    //viewflipper for view
    ViewFlipper viewFlipper;
    //listview for view
    ListView currentRoadWorks, plannedRoadWorks;
    //search view for view
    SearchView searchView;
    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, SplashActivity.class);
           //     startActivity(homeIntent);
                //finish();
            }
        }, SPLASH_TIME_OUT);

        //start view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind button to id
        Button = (Button) findViewById(R.id.button0);
        Button1 = (Button) findViewById(R.id.button1);
        //set view vlipper
        viewFlipper = (ViewFlipper) findViewById(R.id.ViewFlipper01);
        //set up and bind the list to current roadworks view
        currentRoadWorks = (ListView) findViewById(R.id.currentRoadWorks);
        currentRoadWorks.setTextFilterEnabled(true);
        //set up and bind planned roadworks to the planned incidents view
        plannedRoadWorks = (ListView) findViewById(R.id.plannedRoadWorks);
        plannedRoadWorks.setTextFilterEnabled(true);

        //bind searh view to the search view
        searchView = (SearchView) findViewById(R.id.searchView1);

//set up search view
        searchView.setQueryHint("Search For Current Roadworks");
        searchView.setBackgroundColor(Color.TRANSPARENT);


        Button.setOnClickListener(Buttonlistener);
        Button1.setOnClickListener(Buttonlistener);
        Button.setBackgroundColor(Color.LTGRAY);

        //calls the xml parser to parse data asynchronously
        ArrayList<String> current ;
        CurrentRoadWorksParser getCurrentXML = new CurrentRoadWorksParser();
        getCurrentXML.execute();
        current = getCurrentXML.getCurrentRoadWorks();
      //  calls xml parser to parse cml arynchrounously
        ArrayList<String> planned ;
        PlannedRoadWorksParser getPlannedXML = new PlannedRoadWorksParser();
        getPlannedXML.execute();
        planned = getPlannedXML.getPlannedRoadWorks();

        // Binding data
        final ArrayAdapter Current = new ArrayAdapter(this, android.R.layout.simple_list_item_1, current);
        final PlannedRoadworksAdapter Planned = new PlannedRoadworksAdapter(this, android.R.layout.simple_list_item_1, planned);


        plannedRoadWorks.setAdapter(Current);
        currentRoadWorks.setAdapter(Planned);

        //search binding
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                //filters planned details when searched
                Planned.getFilter().filter(text);
                //filters current details when searched
                Current.getFilter().filter(text);
                return false;
            }
        });






    }

    //button listner to switch views
    private View.OnClickListener Buttonlistener = new View.OnClickListener() {
        //pases through view
        public void onClick(View v) {
            //looks for right id
            switch (v.getId()) {
                //changes to the current roadworks
                case R.id.button0:
                    viewFlipper.setDisplayedChild(0);
                    Button.setBackgroundColor(Color.LTGRAY);
                    Button1.setBackgroundColor(Color.WHITE);

                    searchView.setQueryHint("Search For Current Roadworks");
                    break;

                //changes to planned roadworks
                case R.id.button1:
                    viewFlipper.setDisplayedChild(1);
                    Button1.setBackgroundColor(Color.LTGRAY);
                    Button.setBackgroundColor(Color.WHITE);

                    searchView.setQueryHint("Search For Planned Roadworks");
                    break;
            }
        }
    };


}
