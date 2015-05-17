package com.codepath.gridimagesearch.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.gridimagesearch.R;

public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //getSupportActionBar().hide();
        getSupportActionBar().setTitle(R.string.settings_action_bar_label);


        //setCurrentSettings();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return false;
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

    /**
     * Persists the image filters into the app's preferences and indicates back to the Search Activity
     * whether or not any preference has changed
     *
     * @param view View
     */
    public void saveSettings(View view) {

        // Get each setting
        Spinner sSelectSize = (Spinner) findViewById(R.id.sSelectSize);
        String size = sSelectSize.getSelectedItem().toString();

        Spinner sSelectColor = (Spinner) findViewById(R.id.sSelectColor);
        String color = sSelectColor.getSelectedItem().toString();

        Spinner sSelectType = (Spinner) findViewById(R.id.sSelectType);
        String type = sSelectType.getSelectedItem().toString();

        EditText etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
        String site = etSiteFilter.getText().toString();

        // Save settings and see if any has changed
        SettingsModel settingsModel = new SettingsModel(this);
        Boolean hasChanged = settingsModel.savePreferences(size, color, type, site);

        Intent returnData = new Intent();
        returnData.putExtra("hasChanged", hasChanged);

        setResult(RESULT_OK, returnData);
        finish();
    }

}
