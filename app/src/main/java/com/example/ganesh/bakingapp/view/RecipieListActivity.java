package com.example.ganesh.bakingapp.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import com.example.ganesh.bakingapp.IdlingResource.IdlingTestResource;
import com.example.ganesh.bakingapp.IdlingResource.IdlingTestResource;
import com.example.ganesh.bakingapp.R;
import com.example.ganesh.bakingapp.adapter.RecipeListAdapter;
import com.example.ganesh.bakingapp.data.BakingConsts;
import com.example.ganesh.bakingapp.data.BakingJsonUtils;
import com.example.ganesh.bakingapp.data.RecipeList;
import com.example.ganesh.bakingapp.view.RecipeListFragment;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipieListActivity extends AppCompatActivity implements RecipeListFragment.OnFragmentInteractionListener, RecipeListAdapter.RecipeClickListener{

    @Nullable
    private IdlingTestResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie_list);
    }

    @VisibleForTesting
    @NonNull
    public IdlingTestResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new IdlingTestResource();
        }
        return mIdlingResource;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRecipeListSelected(RecipeList recipeList) {
        Intent intent = new Intent(this,DetailActivity.class);
        Bundle bundle = new Bundle();
        ArrayList<RecipeList> recipeLists = new ArrayList<>();
        recipeLists.add(recipeList);
        bundle.putParcelableArrayList(BakingConsts.selected_recipie,recipeLists);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
