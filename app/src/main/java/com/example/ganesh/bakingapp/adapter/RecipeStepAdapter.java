package com.example.ganesh.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ganesh.bakingapp.R;
import com.example.ganesh.bakingapp.data.RecipeList;
import com.example.ganesh.bakingapp.data.Step;
import com.example.ganesh.bakingapp.view.DetailActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepListHolder> {
    public RecipeStepAdapter(Context context) {
    }


    public interface StepClickListener {
        public void onStepSelected(int stepNumber,List<Step> recipeSteps ,String recipeName);
    }

    public void setStepClickListener(StepClickListener mStepClickListener) {
        this.mStepClickListener = mStepClickListener;
    }

    StepClickListener mStepClickListener;
    List<Step> mRecipeSteps;
    String recipeName;

    public class RecipeStepListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView stepDescription;
        public RecipeStepListHolder(View itemView) {
            super(itemView);
            stepDescription = (TextView)itemView.findViewById(R.id.step_description);
            stepDescription.setOnClickListener(this);

        }
        public void bindView(int position) {
            stepDescription.setText(mRecipeSteps.get(position).getShortDescription());
        }

        @Override
        public void onClick(View view) {
            mStepClickListener.onStepSelected(getAdapterPosition(),mRecipeSteps,recipeName);
        }
    }
    @Override
    public RecipeStepListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParent = false;
        View view = inflater.inflate(R.layout.detail_step_item,parent,shouldAttachToParent);
        return new RecipeStepListHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeStepListHolder holder, int position) {

        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        if(mRecipeSteps != null && mRecipeSteps.size() > 0) {

            return mRecipeSteps.size();
        }
        return 0;
    }

    public void stepReceipeData(List<Step> selectedSteps,String recipeName){
        this.mRecipeSteps = selectedSteps;
        this.recipeName = recipeName;
        notifyDataSetChanged();
    }
}
