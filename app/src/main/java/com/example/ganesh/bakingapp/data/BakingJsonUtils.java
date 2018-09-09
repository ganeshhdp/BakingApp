package com.example.ganesh.bakingapp.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import okhttp3.OkHttpClient;

public class BakingJsonUtils {

public interface IRecipe {
    @GET("baking.json")
    Call<ArrayList<RecipeList>> getRecipeList();
}



public static IRecipe recipe = null;
public static IRecipe getRecipe(){

    //HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();


    Gson gson = new GsonBuilder().create();
    if(recipe == null){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BakingConsts.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(httpClientBuilder.build())
                .build();
        recipe = retrofit.create(IRecipe.class);
    }
    return recipe;
}
}