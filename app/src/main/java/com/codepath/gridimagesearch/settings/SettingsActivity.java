package com.codepath.gridimagesearch.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.codepath.gridimagesearch.R;

public class SettingsActivity extends ActionBarActivity {

    Spinner sSelectSize;
    Spinner sSelectColor;
    Spinner sSelectType;
    EditText etSiteFilter;
    SettingsModel settingsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //getSupportActionBar().hide();
        getSupportActionBar().setTitle(R.string.settings_action_bar_label);

        setupViews();

        // Load settings from persistent storage
        settingsModel = new SettingsModel(this);

        setCurrentSettings();
    }

    /**
     * Get a reference to each view in the corresponding layout
     */
    protected void setupViews() {
        sSelectSize = (Spinner) findViewById(R.id.sSelectSize);
        sSelectColor = (Spinner) findViewById(R.id.sSelectColor);
        sSelectType = (Spinner) findViewById(R.id.sSelectType);
        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
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
        String size = sSelectSize.getSelectedItem().toString();
        String color = sSelectColor.getSelectedItem().toString();
        String type = sSelectType.getSelectedItem().toString();
        String site = etSiteFilter.getText().toString();

        // Save settings and see if any has changed
        Boolean hasChanged = settingsModel.savePreferences(size, color, type, site);

        Intent returnData = new Intent();
        returnData.putExtra("hasChanged", hasChanged);

        setResult(RESULT_OK, returnData);
        finish();
    }

    /**
     * Sets the spinners and the site filter to the current saved state
     */
    public void setCurrentSettings() {
        setSpinnerToValue(sSelectSize, settingsModel.size);
        setSpinnerToValue(sSelectColor, settingsModel.color);
        setSpinnerToValue(sSelectType, settingsModel.type);

        if (!settingsModel.site.equals("")) {
            etSiteFilter.setText(settingsModel.site);
        }
    }

    /**
     * Sets Spinner to given string value
     * @param spinner Spinner
     * @param value String
     */
    protected void setSpinnerToValue(Spinner spinner, String value) {
        int index = 0;
        SpinnerAdapter adapter = spinner.getAdapter();
        for (int i = 0; i < adapter.getCount() ; i++) {
            if (adapter.getItem(i).toString().toLowerCase().equals(value.toLowerCase())) {
                spinner.setSelection(i);
                return;
            }
        }
    }

}
