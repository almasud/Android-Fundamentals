package com.example.almasud.fundamental.map_location;

import android.content.Context;
import android.content.SharedPreferences;

public class GeofencingSessionManager {
    // Reference of Shared Preference .
    private SharedPreferences pref;
    // Editor reference for shared preference.
    private SharedPreferences.Editor editor;
    // Activity context
    private Context context;
    // Shared Preference file name
    static final String PREF_NAME = "GeofencePrefs";
    // Monitoring is ON or not.
    String IS_MONITORING = "IsMonitoring";

    /**
     * A constructor that initialize the field values and
     * return the reference of this class.
     * @param context Context of the current class.
     */
    GeofencingSessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    /**
     * Create a session for monitoring or not
     */
    void createMonitorSession() {
        // Storing monitor value as true.
        editor.putBoolean(IS_MONITORING, true);
        // Commit changes
        editor.commit();
    }

    // Clear login session
    void removeMonitorSession() {
        // Cleaning all data in Shared Preference.
        editor.clear();
        editor.commit();
    }

    // Check for Monitoring
    boolean isMonitoring(){
        //  If IS_MONITORING preference does not exist it returns false
        return pref.getBoolean(IS_MONITORING, false);
    }
}
