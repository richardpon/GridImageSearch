package com.codepath.gridimagesearch.images;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ImageResult implements Serializable {

    public String fullUrl;
    public String thumbUrl;
    public String title;

    /**
     * @param json JSONObject
     */
    public ImageResult(JSONObject json) {
        try {
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");
        } catch (JSONException e) {

        }
    }

    /**
     * Take an array of json images and result arrayList of image results
     * @param array JSONArray
     * @return ArrayList<ImageResult>
     */
    public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();

        for (int i = 0 ; i < array.length() ; i++) {
            try {
                results.add(new ImageResult(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
