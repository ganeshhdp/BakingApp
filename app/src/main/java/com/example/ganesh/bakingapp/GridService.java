package com.example.ganesh.bakingapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.ganesh.bakingapp.data.BakingConsts;
import com.example.ganesh.bakingapp.data.RecipeList;

import java.util.ArrayList;
import java.util.List;

public class GridService extends RemoteViewsService {
    List<String> mRemoteViewingredientsList;
    ArrayList<RecipeList> recipeList;

    public GridService() {
    }


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        System.out.println("lanka in onGetViewFactory");
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }


    class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context mContext = null;

        public GridRemoteViewsFactory(Context applicationContext) {
            mContext = applicationContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
//            mRemoteViewingredientsList =BakingWidget .getIngredientsList();
            recipeList = BakingWidget.getRecipelist();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return recipeList.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {

            RemoteViews views = new RemoteViews(mContext.getPackageName(),
                    R.layout.widget_grid_item);
            views.setTextViewText(R.id.widget_grid_view_item, recipeList.get(i).getName());
            Intent fillInIntent = new Intent();
            Bundle bundle = new Bundle();
            ArrayList<RecipeList> recipeLists = new ArrayList<>();
            recipeLists.add(recipeList.get(i));
            bundle.putParcelableArrayList(BakingConsts.selected_recipie,recipeLists);
            fillInIntent.putExtras(bundle);
            views.setOnClickFillInIntent(R.id.widget_grid_view_item, fillInIntent);

            return views;        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
