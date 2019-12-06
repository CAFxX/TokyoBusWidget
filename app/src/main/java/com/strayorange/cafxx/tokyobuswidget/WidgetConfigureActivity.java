package com.strayorange.cafxx.tokyobuswidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * The configuration screen for the {@link Widget Widget} AppWidget.
 */
public class WidgetConfigureActivity extends Activity {

    private static final String PREFS_NAME = "com.strayorange.cafxx.tokyobuswidget.Widget";
    private static final String PREF_LOCATION_KEY_PREFIX = "loc_";

    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private Spinner locationPicker;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = WidgetConfigureActivity.this;

            // When the button is clicked, store the string locally
            String location = locationPicker.getSelectedItem().toString();
            saveLocationPref(context, mAppWidgetId, location);

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            Widget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    public WidgetConfigureActivity() {
        super();
    }

    static void deletePrefs(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_LOCATION_KEY_PREFIX + appWidgetId);
        prefs.apply();
    }

    static String loadLocationPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(PREF_LOCATION_KEY_PREFIX + appWidgetId, Timetable.loc.Roppongi.toString());
    }

    private static void saveLocationPref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_LOCATION_KEY_PREFIX + appWidgetId, text);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.widget_configure);
        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

        // Populate the spinner with the locations
        String[] locations = {
                Timetable.loc.Roppongi.toString(),
                Timetable.loc.Shibuya.toString()
        };
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, locations);
        locationPicker = findViewById(R.id.locationPicker);
        locationPicker.setAdapter(spinnerArrayAdapter);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        String ls = loadLocationPref(WidgetConfigureActivity.this, mAppWidgetId);
        locationPicker.setSelection(spinnerArrayAdapter.getPosition(ls));
    }
}

