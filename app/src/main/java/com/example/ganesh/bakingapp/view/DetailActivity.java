package com.example.ganesh.bakingapp.view;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ganesh.bakingapp.R;
import com.example.ganesh.bakingapp.adapter.RecipeStepAdapter;
import com.example.ganesh.bakingapp.data.BakingConsts;
import com.example.ganesh.bakingapp.data.RecipeList;
import com.example.ganesh.bakingapp.data.Step;

import java.util.ArrayList;

import  com.example.ganesh.bakingapp.data.BakingConsts;

public class DetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnFragmentInteractionListener, RecipeStepAdapter.StepClickListener, StepDetailFragment.OnFragmentInteractionListener{

    private ArrayList<RecipeList> recipeLists;
    RecipeList selectedRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(bundle);
        recipeLists = bundle.getParcelableArrayList(BakingConsts.selected_recipie);
        selectedRecipe = recipeLists.get(0);
        if(selectedRecipe!=null){
            getSupportActionBar().setTitle(selectedRecipe.getName());
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                .commit();

        if(findViewById(R.id.detail_layout)!=null && findViewById(R.id.detail_layout).getTag().equals("tablet-land")){
            StepDetailFragment step_fragment = new StepDetailFragment();
            Bundle step_bundle = new Bundle();
            step_bundle.putInt(BakingConsts.selected_step_number,0);
            step_bundle.putParcelable(BakingConsts.selected_recipie,selectedRecipe);
            step_fragment.setArguments(step_bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container2,step_fragment)
                    .addToBackStack("StepSelected")
                    .commit();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelableArrayList(BakingConsts.selected_recipie,recipeLists);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onStepSelected(int stepNumber) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BakingConsts.selected_step_number,stepNumber);
        bundle.putParcelable(BakingConsts.selected_recipie,selectedRecipe);
        fragment.setArguments(bundle);
        if(findViewById(R.id.detail_layout)!=null && findViewById(R.id.detail_layout).getTag().equals("tablet-land")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragment)
                    .addToBackStack("StepSelected")
                    .commit();
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                    .addToBackStack("StepSelected")
                    .commit();
        }
    }
}
