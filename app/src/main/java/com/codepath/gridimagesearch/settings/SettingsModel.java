package com.codepath.gridimagesearch.settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingsModel {

    private final static String defaultSetting = "any";
    private SharedPreferences prefs;

    public String size;
    public String color;
    public String type;
    public String site;

    private Context context;

    /**
     * @param context Context
     */
    public SettingsModel(Context context) {
        this.context = context;

        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        loadPreferences();
    }

    /**
     *
     * @param size String
     * @param color String
     * @param type String
     * @param site String
     * @return boolean true if any preference has changed, false if no preference has changed
     */
    public boolean savePreferences(String size, String color, String type, String site) {
        SharedPreferences.Editor edit = prefs.edit();

        boolean hasChanged = false;

        // Size
        if (!this.size.equals(size)) {
            edit.putString("size", size);
            this.size = size;
            hasChanged = true;
        }

        // Color
        if (!this.color.equals(color)) {
            edit.putString("color", color);
            this.color = color;
            hasChanged = true;
        }

        // Type
        if (!this.type.equals(type)) {
            edit.putString("type", type);
            this.type = type;
            hasChanged = true;
        }

        // Site
        if (!this.site.equals(site)) {
            edit.putString("site", site);
            this.site = site;
            hasChanged = true;
        }

        // Commit changes if any preferences has changed
        if (hasChanged) {
            edit.commit();
        }

        return hasChanged;
    }

    /**
     * Loads preferences into this
     */
    public void loadPreferences() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        size = pref.getString("size", defaultSetting);
        color = pref.getString("color", defaultSetting);
        type = pref.getString("type", defaultSetting);
        site = pref.getString("site", defaultSetting);
    }

}
