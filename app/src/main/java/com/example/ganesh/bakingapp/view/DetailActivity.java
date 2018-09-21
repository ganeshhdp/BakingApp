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
import java.util.List;

import  com.example.ganesh.bakingapp.data.BakingConsts;

public class DetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnFragmentInteractionListener, RecipeStepAdapter.StepClickListener, StepDetailFragment.OnFragmentInteractionListener{

    private ArrayList<RecipeList> recipeLists;
    RecipeList selectedRecipe;
    private boolean isStepSelected;
    private String recipeName;
    private int stepSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_detail);
        if(savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(bundle);
            recipeLists = bundle.getParcelableArrayList(BakingConsts.selected_recipie);
            selectedRecipe = recipeLists.get(0);
            recipeName = selectedRecipe.getName();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                    .commit();

            if (findViewById(R.id.detail_layout) != null && findViewById(R.id.detail_layout).getTag().equals("tablet-land")) {
                startStepFragment(0, true);
            }
        } else {
            recipeName = savedInstanceState.getString(BakingConsts.selected_recipie);
        }
        if (selectedRecipe != null) {
            getSupportActionBar().setTitle(recipeName);
        }

    }

    private void startStepFragment(int stepNumber,boolean tablet){
        StepDetailFragment step_fragment = new StepDetailFragment();
        Bundle step_bundle = new Bundle();
        step_bundle.putInt(BakingConsts.selected_step_number,stepNumber);
        step_bundle.putParcelable(BakingConsts.selected_recipie,selectedRecipe);
        step_bundle.putString("Recipe Name",selectedRecipe.getName());
        step_fragment.setArguments(step_bundle);
        getSupportFragmentManager().beginTransaction().replace(tablet ? R.id.container2:R.id.container,step_fragment)
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(BakingConsts.selected_recipie,recipeName);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onStepSelected(int stepNumber, List<Step> mSteps,String recipeName) {
        isStepSelected = true;
        stepSelected = stepNumber;
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle bundle = new Bundle();

        bundle.putInt(BakingConsts.selected_step_number,stepNumber);
        bundle.putParcelableArrayList(BakingConsts.selected_recipie_steps,(ArrayList<Step>)mSteps);
        bundle.putString("Recipe Name",recipeName);
        fragment.setArguments(bundle);
        if(findViewById(R.id.detail_layout)!=null && findViewById(R.id.detail_layout).getTag().equals("tablet-land")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragment)
                    .commit();
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                    .commit();
        }
    }
}
