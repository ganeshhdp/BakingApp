package com.example.ganesh.bakingapp.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ganesh.bakingapp.R;
import com.example.ganesh.bakingapp.data.BakingConsts;
import com.example.ganesh.bakingapp.data.RecipeList;
import com.example.ganesh.bakingapp.data.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepDetailFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private SimpleExoPlayerView mExoPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private Step selectedStep;
    private List<Step> mSelectedSteps;
    private int selectedStepNumber;
    TextView textDescription;

    private Handler mHandler;
    DetailActivity mStepSelectedListener;
    private DefaultBandwidthMeter mDefaultBandwidthMeter;
    private Button previousButton;
    private Button nextButton;
    private RecipeList selectedRecipe;
    private View convertView;
    private String recipeName;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("Selected Step",selectedStep);
        outState.putParcelableArrayList(BakingConsts.selected_recipie_steps,(ArrayList<Step>)mSelectedSteps);
        outState.putString("Recipe Name",this.recipeName);
        super.onSaveInstanceState(outState);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StepDetailFragment newInstance(String param1, String param2) {
        StepDetailFragment fragment = new StepDetailFragment();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for
        mStepSelectedListener = (DetailActivity)getActivity();
        if(savedInstanceState!=null){
            selectedStep = savedInstanceState.getParcelable("Selected Step");
            mSelectedSteps = savedInstanceState.getParcelableArrayList(BakingConsts.selected_recipie_steps);
            recipeName = savedInstanceState.getString("Recipe Name");
        }
        else {
            mSelectedSteps = getArguments().getParcelableArrayList(BakingConsts.selected_recipie_steps);
            this.recipeName = getArguments().getString("Recipe Name");
            if(mSelectedSteps == null){
                selectedRecipe = getArguments().getParcelable(BakingConsts.selected_recipie);
                selectedStepNumber = 0;
                mSelectedSteps = selectedRecipe.getSteps();

            }
            else {
                selectedStepNumber = getArguments().getInt(BakingConsts.selected_step_number);
            }
            selectedStep = mSelectedSteps.get(selectedStepNumber);
        }
        ((DetailActivity)getActivity()).getSupportActionBar().setTitle(this.recipeName);
        View convertView =  inflater.inflate(R.layout.fragment_step_detail, container, false);
        textDescription = (TextView)convertView.findViewById(R.id.recipe_step_detail_text);
        previousButton = (Button)convertView.findViewById(R.id.recipe_previous_step);
        nextButton = (Button) convertView.findViewById(R.id.recipe_next_step);
        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        mExoPlayerView = (SimpleExoPlayerView)convertView.findViewById(R.id.video_player);

        initializePlayer(selectedStep);
        this.convertView = convertView;
        return convertView;
    }

    private void initializePlayer(Step selectedStep) {
        mHandler = new Handler();
        mDefaultBandwidthMeter = new DefaultBandwidthMeter();
        String stepDescription = selectedStep.getDescription();
        textDescription.setText(stepDescription);
        mExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        String videoUrl = selectedStep.getVideoURL();

        if(!videoUrl.isEmpty()){
            if(mExoPlayer == null){
                //TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(mDefaultBandwidthMeter);
                //DefaultTrackSelector trackSelector = new DefaultTrackSelector(mHandler, videoTrackSelectionFactory);
                //TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(mDefaultBandwidthMeter);
                DefaultTrackSelector trackSelector = new DefaultTrackSelector(mHandler);
                LoadControl loadControl = new DefaultLoadControl();
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
                mExoPlayerView.setPlayer(mExoPlayer);
                String userAgent = Util.getUserAgent(getContext(),"BakinAPP");
                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl),
                        new DefaultDataSourceFactory(getContext(), userAgent),
                        new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(true);
            }
        }
        else {
            mExoPlayerView.setVisibility(View.GONE);
            return;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mExoPlayer != null){
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if(selectedStep != null){
            initializePlayer(selectedStep);
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
        if(mExoPlayer!=null){
            mExoPlayer.stop();
            mExoPlayer.release();
        }

    }

    @Override
    public void onClick(View view) {
        int new_step_number = 0;
        int number_of_steps = mSelectedSteps.size();
        switch(view.getId()){
            case R.id.recipe_previous_step:
                if(selectedStepNumber < number_of_steps){
                    selectedStepNumber = selectedStepNumber - 1;
                }
                if(selectedStepNumber < 0){
                    selectedStepNumber = number_of_steps -1;
                }
                break;

            case R.id.recipe_next_step:
                if(selectedStepNumber < number_of_steps){
                    selectedStepNumber = selectedStepNumber + 1;
                }
                if(selectedStepNumber == number_of_steps){
                    selectedStepNumber = 0;
                }
                break;
        }
        mStepSelectedListener.onStepSelected(selectedStepNumber,mSelectedSteps,recipeName);
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
