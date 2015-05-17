package com.codepath.gridimagesearch.mainsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.imagedetail.ImageDisplayActivity;
import com.codepath.gridimagesearch.images.GoogleApiClient;
import com.codepath.gridimagesearch.images.ImageResult;
import com.codepath.gridimagesearch.images.ImageResultsAdapter;
import com.codepath.gridimagesearch.settings.SettingsActivity;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {

    private static final String TAG = "SearchActivity";
    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();

        getSupportActionBar().setTitle(R.string.search_action_bar_label);

        //creates datasource
        imageResults = new ArrayList<ImageResult>();
        // attaches adapter
        aImageResults = new ImageResultsAdapter(this, imageResults);
        //link adapter to gridview
        gvResults.setAdapter(aImageResults);

        googleApiClient = new GoogleApiClient(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        //MenuItem settingsItem = menu.findItem(R.id.action_settings);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // launch the image display activity
                // create intent (inside anonymous class so this refers to anonymous class)
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);

                // get the image result to display
                ImageResult result = imageResults.get(position);

                // pass image result into intent (Serializable or Parcelable)
                i.putExtra("result", result);

                // Launch the new activity
                startActivity(i);
            }
        });
    }

    // Fired when button is pressed
    public void onImageSearch(View v) {
        String query = etQuery.getText().toString();

        googleApiClient.doImageSearch(query);
    }

    /**
     * @param newImageResults ArrayList<ImageResult>
     * @return void
     */
    public void setImages(ArrayList<ImageResult> newImageResults) {
        imageResults.clear();
        aImageResults.addAll(newImageResults);
    }

    public void openSearchSettings(MenuItem menuItem) {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
        Log.i(TAG, "search!");
        Log.i(TAG, "search!#@$");

    }

}
