package com.benrostudios.xpenso.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.benrostudios.xpenso.ui.MainActivity;
import com.benrostudios.xpenso.R;
import com.benrostudios.xpenso.db.Expenses;
import com.benrostudios.xpenso.utils.TransactionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    private static final String INGRE = "TRANSACTION" ;
    private static final int WIDGET_PENDING_INTENT_ID = 9;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = getTransactionListRemoteView(context, appWidgetId);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    static void updateAppWidgetTitle(Context context, AppWidgetManager appWidgetManager,
                                     int appWidgetId) {
        RemoteViews views = getTitleRemoteView(context);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static RemoteViews getTitleRemoteView(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        String ingredientString = sharedPreferences.getString(context.getString(R.string.pref_transaction_list_key), "");


        // Convert transaction string to the list of ingredients
        List<Expenses> expensesList = (List<Expenses>) TransactionUtils.toExpensesList(ingredientString);

        // Create an Intent to launch MainActivity or DetailActivity when clicked
        Intent intent;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        // If a user does not click the recipe item, there is no data to launch the DetailActivity.
        // In this case, launch the MainActivity. Otherwise, launch the DetailActivity.
        if (expensesList.isEmpty()) {
            intent = new Intent(context, MainActivity.class);
            // Display the app name in the app widget
            views.setTextViewText(R.id.widget_recipe_name, context.getString(R.string.app_name));
        } else {
            intent = new Intent(context, MainActivity.class);
            Expenses expenses = expensesList.get(0);
            intent.putExtra(INGRE,(Serializable) expenses);
        }

        // Widgets allow click handlers to only launch pending intents
        PendingIntent pendingIntent = PendingIntent.getActivity(context, WIDGET_PENDING_INTENT_ID,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_recipe_name, pendingIntent);

        return views;
    }


    private static RemoteViews getTransactionListRemoteView(Context context, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        // Set up the intent that starts the ListWidgetService, which will provide the views for
        // this collection
        Intent intent = new Intent(context, ListWidgetService.class);
        // Add the app widget Id to the intent extras
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        views.setRemoteAdapter(R.id.widget_list_view, intent);

        // Handle empty view
        views.setEmptyView(R.id.widget_list_view, R.id.widget_empty_view);
        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            updateAppWidgetTitle(context, appWidgetManager, appWidgetId);
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

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        String action = intent.getAction();
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {
            int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            }
            // Trigger data update to handle the ListView widgets and force a data refresh
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
        }
        super.onReceive(context, intent);
    }
}

