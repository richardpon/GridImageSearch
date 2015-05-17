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

    public void doImageSearchInitial(String query, String size, String color, String type, String site) {

        String searchUrl = buildUrl(query, size, color, type, site, 0);

        Log.i(TAG, "searchingUrl="+searchUrl);

        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Log.i(TAG, response.toString());

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

    public void doImageSearchSubsequent(String query, String size, String color, String type, String site, int page) {

        String searchUrl = buildUrl(query, size, color, type, site, page);

        Log.i(TAG, "searchingUrl="+searchUrl);

        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Log.i(TAG, response.toString());

                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");

                    ((SearchActivity) context).addImages(ImageResult.fromJSONArray(imageResultsJson));
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
    private String buildUrl(String query, String size, String color, String type, String site, int page) {
        String url = baseUrl;
        url += "&q="+query;
        url += "&start="+(page*8);

        // Set size
        if (!size.toLowerCase().equals("any")) {
            url += "&imgsz="+size.toLowerCase();
        }

        // Set color
        if (!color.toLowerCase().equals("any")) {
            url += "&imgcolor="+color.toLowerCase();
        }

        // Set type
        if (!type.toLowerCase().equals("any")) {
            url += "&imgtype="+type.toLowerCase();
        }

        // Set site
        if (!site.toLowerCase().equals("")) {
            url += "&as_sitesearch="+site.toLowerCase();
        }

        return url;
    }

}
