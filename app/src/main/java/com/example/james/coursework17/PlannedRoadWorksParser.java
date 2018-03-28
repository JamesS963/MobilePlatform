package com.example.james.coursework17;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by James on 24/03/2018.
 */

public class PlannedRoadWorksParser extends AsyncTask {
    // url string to call the api
    URL url;
    // array list of strings to be displayed in the android app
    ArrayList<String> currentRoadWorks = new ArrayList<>();
    // title of the road incident
    String title = "";
    // description of the road incident
    String description = "";

    // the entire string file of the road incident
    String result = "";

    //asynchronous call to get the data
    @Override
    protected Object doInBackground(Object[] objects) {
        //try catch block
        try {
            // sets the url
            url = new URL("http://trafficscotland.org/rss/feeds/plannedroadworks.aspx");//changed to planned!!!

            //new instance of the pullparcr factory
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);

            //new instance of the pull parser
            XmlPullParser pullParser = factory.newPullParser();

            //creates the input stream on the pullparser
            pullParser.setInput(url.openConnection().getInputStream(), "UTF_8");


            // gets the event type for comparison
            int eventType = pullParser.getEventType();
            //while loop to go through the document if the document hasn't ended
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //checks if it is the start tag
                if (eventType == XmlPullParser.START_TAG) {
                    //checks if the item tag is found in the xml and if so sets in
                    if (pullParser.getName().equalsIgnoreCase("item")) {

                    } else if (pullParser.getName().equalsIgnoreCase("title")) {

                            title = (pullParser.nextText());
                    } else if (pullParser.getName().equalsIgnoreCase("description")) {

                            description = (pullParser.nextText().replace("<br />", "\n"));

                    }
                } else if (eventType == XmlPullParser.END_TAG && pullParser.getName().equalsIgnoreCase("item")) {


                    result = "title: " + title + "\n"+"Details:"+"\n\n" + description + "\n";
                    currentRoadWorks.add(result);
                }


                eventType = pullParser.next(); //move to next element
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentRoadWorks.set(0, "Planned Roadworks");
        return currentRoadWorks;

    }

    public ArrayList<String> getPlannedRoadWorks()
    {
        return currentRoadWorks;
    }
}
