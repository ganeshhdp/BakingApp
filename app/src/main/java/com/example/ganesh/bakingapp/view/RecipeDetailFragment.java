package com.example.ganesh.bakingapp.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ganesh.bakingapp.R;
import com.example.ganesh.bakingapp.adapter.RecipeStepAdapter;
import com.example.ganesh.bakingapp.data.BakingConsts;
import com.example.ganesh.bakingapp.data.Ingredient;
import com.example.ganesh.bakingapp.data.RecipeList;
import com.example.ganesh.bakingapp.data.Step;
import com.example.ganesh.bakingapp.widget.BakingService;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecipeList selectedRecipe;
    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeDetailFragment newInstance(String param1, String param2) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        RecyclerView recipeListView = (RecyclerView)convertView.findViewById(R.id.recipe_step_list);
        ArrayList<RecipeList> recipeLists = getArguments().getParcelableArrayList(BakingConsts.selected_recipie);
        selectedRecipe = recipeLists.get(0);
        ArrayList<String> recipeIngredients = new ArrayList<>();
        if(selectedRecipe!=null) {
            final RecipeStepAdapter adapter = new RecipeStepAdapter((DetailActivity) getActivity());
            List<Ingredient> ingredientList = selectedRecipe.getIngredients();


            TextView ingredientText = (TextView) convertView.findViewById(R.id.ingredient_text);
            for (Ingredient ingredient : ingredientList) {
                String ingredientReceived = ingredient.getIngredient();
                Double quantityReceived = ingredient.getQuantity();
                String measureReceived = ingredient.getMeasure();
                ingredientText.append(ingredientReceived + "\t\t\t : \t\t\t");
                ingredientText.append(quantityReceived + " ");
                ingredientText.append(measureReceived + "\n\n");

                recipeIngredients.add(ingredientReceived + "\n" +
                        "Quantity: " + quantityReceived + "\n" +
                        "Measure: " + measureReceived + "\n");
            }
            recipeListView.setAdapter(adapter);
            adapter.setStepClickListener((DetailActivity) getActivity());
            adapter.stepReceipeData(selectedRecipe.getSteps());
        }
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getContext());
        recipeListView.setLayoutManager(layoutManager);
//        BakingService.startBakingService(getContext(),recipeIngredients);
        return convertView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
