package com.strayorange.cafxx.tokyobuswidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link WidgetConfigureActivity WidgetConfigureActivity}
 */
public class Widget extends AppWidgetProvider {

    private static final String ACTION_SCHEDULED_UPDATE = "com.strayorange.cafxx.tokyobuswidget.SCHEDULED_UPDATE";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        // Populate the fields
        Timetable.loc l = Location.get(context, appWidgetId);
        views.setTextViewText(R.id.textView_top, l.toString());
        views.setTextViewText(R.id.appwidget_text, Timetable.get(l).findFirst().orElse("¯\\_(ツ)_/¯"));
        views.setTextViewText(R.id.textView_bottom, Timetable.get(l).skip(1).limit(2).collect(Collectors.joining("  ")));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void scheduleUpdate(Context context) {
        AlarmManager alarmManager = (AlarmManager) Objects.requireNonNull(context.getSystemService(Context.ALARM_SERVICE));
        // Substitute AppWidget for whatever you named your AppWidgetProvider subclass
        Intent intent = new Intent(context, Widget.class);
        intent.setAction(ACTION_SCHEDULED_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        // For API 19 and later, set may fire the intent a little later to save battery,
        // setExact would ensure the intent goes off exactly at midnight, but we don't care.
        alarmManager.set(AlarmManager.RTC_WAKEUP, java.time.LocalTime.now().plusMinutes(1).getNano() / 1000000, pendingIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals(ACTION_SCHEDULED_UPDATE)) {
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            int[] ids = manager.getAppWidgetIds(new ComponentName(context, Widget.class));
            onUpdate(context, manager, ids);
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        scheduleUpdate(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            WidgetConfigureActivity.deletePrefs(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

