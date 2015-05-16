package com.codepath.gridimagesearch.images;


import android.content.Context;
import android.util.Log;

import com.codepath.gridimagesearch.mainsearch.SearchActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Responsible for talking to the Google API to fetch images
 */
public class GoogleApiClient {

    private static final String TAG = "GoogleApiClient";
    private Context context;
    private String baseUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&safe=active&imgsz=medium";
    AsyncHttpClient client;

    public GoogleApiClient(Context context) {
        this.context = context;
        client = new AsyncHttpClient();
    }

    public void doImageSearch(String query) {

        String searchUrl = buildUrl(query);

        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess ( int statusCode, Header[] headers, JSONObject response){
                Log.d(TAG, response.toString());

                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");

                    ((SearchActivity) context).setImages(ImageResult.fromJSONArray(imageResultsJson));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * Builds the query url for the Google API
     * @param query String
     * @return String
     */
    private String buildUrl(String query) {
        String url = baseUrl;
        url += "&q="+query;
        return url;
    }

}
