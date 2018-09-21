package com.example.ganesh.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.ganesh.bakingapp.data.RecipeList;
import com.example.ganesh.bakingapp.view.DetailActivity;

import java.sql.SQLOutput;
import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidget extends AppWidgetProvider {
    static ArrayList<String> ingredientsList = new ArrayList<>();

    public static ArrayList<RecipeList> getRecipelist() {
        return recipelist;
    }

    static ArrayList<RecipeList> recipelist;



    static void updateAppWidget(Context context,
                                AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.widget_grid_view);

        Intent appIntent = new Intent(context, DetailActivity.class);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.addCategory(Intent.ACTION_MAIN);

        appIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent appPendingIntent = PendingIntent.getActivity(
                context,
                0,
                appIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);

        Intent intent = new Intent(context, GridService.class);
        views.setRemoteAdapter(appWidgetId,R.id.widget_grid_view, intent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static ArrayList<String> getIngredientsList(){
        return ingredientsList;
    }

    public static void updateBakingWidgets(
            Context context,
            AppWidgetManager appWidgetManager,
            int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }



    public static  void updateWidgetDetails(Context context, ArrayList<RecipeList> RecipeSelected){

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, BakingWidget.class));
        recipelist = RecipeSelected;
//        ingredientsList = fromActivityIngredientsList;
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        BakingWidget.updateBakingWidgets(context, appWidgetManager, appWidgetIds);


    }
    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, BakingWidget.class));

        final String action = intent.getAction();

        if (action.equals("android.appwidget.action.APPWIDGET_UPDATE2")) {
            ingredientsList = intent.getExtras().
                    getStringArrayList("Activity_Ingredient_list");
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);

            BakingWidget.updateBakingWidgets(context, appWidgetManager, appWidgetIds);
            super.onReceive(context, intent);
        }
    }
}

