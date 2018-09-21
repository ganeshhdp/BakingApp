package com.example.ganesh.bakingapp.widget;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.example.ganesh.bakingapp.BakingWidget;
import com.example.ganesh.bakingapp.data.RecipeList;

import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BakingService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.ganesh.bakingapp.widget.action.FOO";
    private static final String ACTION_BAZ = "com.example.ganesh.bakingapp.widget.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.ganesh.bakingapp.widget.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.ganesh.bakingapp.widget.extra.PARAM2";

    public BakingService() {
        super("BakingService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    public static void startBakingService(Context context, ArrayList<RecipeList> RecipeList) {
        Intent intent = new Intent(context, BakingService.class);
        intent.putExtra("Activity_Ingredient_list", RecipeList);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ArrayList<RecipeList> RecipeSelected =
                    intent.getExtras().getParcelableArrayList(
                            "Activity_Ingredient_list");
            handleActionUpdateBakingWidgets(RecipeSelected);
        }
    }

    private void handleActionUpdateBakingWidgets(ArrayList<RecipeList> RecipeList) {

        BakingWidget.updateWidgetDetails(this,RecipeList);
        /*Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putExtra("Activity_Ingredient_list", fromActivityIngredientsList);
        sendBroadcast(intent);*/
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
