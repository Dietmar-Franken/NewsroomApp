package com.example.anthony.thenewsroom;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Anthony on 4/11/2017.
 */

public class PCWorldParse {

    public static HashMap<String, String> getPCWorldNews() {

        List<String> headlines = new ArrayList();
        List<String> links = new ArrayList();
        HashMap<String, String> stories = new HashMap<String, String>();

        try {
            URL url = new URL(RssFeedUrls.PCWorld);


            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(url.openConnection().getInputStream(), "UTF_8");

            boolean insideItem = false;

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {

                    if (xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        if (insideItem)
                            headlines.add(xpp.nextText());
                    } else if (xpp.getName().equalsIgnoreCase("link")) {
                        if (insideItem)
                            links.add(xpp.nextText());
                    }
                }else if(eventType== XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
                    insideItem=false;
                }

                eventType = xpp.next();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("error boi","ran "+headlines.size());
        for(int i = 0; i < headlines.size(); i++) {
            stories.put(headlines.get(i),links.get(i));
        }
        return stories;
    }
}
