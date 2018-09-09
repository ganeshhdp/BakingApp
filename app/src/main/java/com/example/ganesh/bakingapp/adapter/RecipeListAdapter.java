package com.example.ganesh.bakingapp.adapter;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganesh.bakingapp.R;
import com.example.ganesh.bakingapp.data.RecipeList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListHolder> {

    public RecipeListAdapter(Context c) {
    }
    public ArrayList<RecipeList> recipeLists;
    public Context context;

    public interface RecipeClickListener{
        public void onRecipeListSelected(RecipeList recipeList);
    }

    RecipeClickListener mClickListener;

    public void setRecipeClickListener(RecipeClickListener recipeClickListener){
        this.mClickListener = recipeClickListener;
    }
    @Override
    public RecipeListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParent = false;
        View view = inflater.inflate(R.layout.recipe_listing_item,parent,shouldAttachToParent);
        return new RecipeListHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeListHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        if(recipeLists!=null && recipeLists.size()>0){

            return recipeLists.size();
        }
        return 0;
    }

    public class RecipeListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView title;
        public RecipeListHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.recipe_image);
            title = (TextView)itemView.findViewById(R.id.recipe_title);
            image.setOnClickListener(this);
        }
        public void bindView(int pos){
            if(!recipeLists.get(pos).getImage().isEmpty()&&recipeLists.get(pos).getImage()!=null){
                Uri imageUri = Uri.parse(recipeLists.get(pos).getImage());
                Picasso.with(context).load(imageUri)
                        .into(image);
            } else {
                Picasso.with(context).load(R.drawable.recipe_temp)
                        .into(image);
            }
            title.setText(recipeLists.get(pos).getName());
        }

        @Override
        public void onClick(View view) {
            System.out.println("ganesh in the onClick of the recipelistadapter with position::"+recipeLists.get(getAdapterPosition()).getSteps());
            mClickListener.onRecipeListSelected(recipeLists.get(getAdapterPosition()));
        }
    }

    public void setRecipeData(ArrayList<RecipeList> recipeData,Context mContext){
        this.recipeLists = recipeData;
        this.context = mContext;
        notifyDataSetChanged();
    }
 }
