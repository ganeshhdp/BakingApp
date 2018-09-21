
package com.example.ganesh.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.example.ganesh.bakingapp.IdlingResource.IdlingTestResource;
import com.example.ganesh.bakingapp.view.RecipieListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;


@RunWith(AndroidJUnit4.class)
//@LargeTest
public class RecipeListTest {
    @Rule
    public ActivityTestRule<RecipieListActivity> mActivityTestRule = new ActivityTestRule<>(RecipieListActivity.class);


    private IdlingTestResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void recipeListCheckTest()  {
        onView(ViewMatchers.withId(R.id.recipe_list)).perform(RecyclerViewActions.scrollToPosition(3));
        onView(withText("Brownies")).check(matches(isDisplayed()));
    }

    @Test
    public void videoPlayerTest () {
        onView(ViewMatchers.withId(R.id.recipe_list)).perform(RecyclerViewActions.scrollToPosition(3));
        onView(withText("Brownies")).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(ViewMatchers.withId(R.id.recipe_step_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(ViewMatchers.withId(R.id.video_player)).check(matches(isDisplayed()));
    }
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}

