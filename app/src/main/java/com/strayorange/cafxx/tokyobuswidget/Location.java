package com.strayorange.cafxx.tokyobuswidget;

import android.content.Context;

class Location {
    static Timetable.loc get(Context context, int appWidgetId) {
        // TODO: get current fused location and return the closest bus station
        String ls = WidgetConfigureActivity.loadLocationPref(context, appWidgetId);
        try {
            return Timetable.loc.valueOf(ls);
        } catch (IllegalArgumentException e) {
            return Timetable.loc.Roppongi;
        }
    }
}
