package com.example.nebo.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.nebo.bakingapp.data.Recipe;
import com.example.nebo.bakingapp.viewholder.RecipeViewHolder;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class BakingActivityTest {
     @Rule public ActivityTestRule<BakingActivity> mActivityTestRule =
            new ActivityTestRule<BakingActivity>(BakingActivity.class);

     private IdlingResource mIdlingResource;

     @Before
     public void registerIdlingResource() {
         // mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
         Espresso.registerIdlingResources(mIdlingResource);
     }

     @Test
     public void hasRecipeBrownie() {
         // Something about a footer not being displayed first, dont know what that is.

         // check to see if the recycler view is already displayed.
        onView(withId(R.id.fl_ingredients)).check(matches(not(isDisplayed())));

        onView(withId(R.id.fl_recipes)).perform(RecyclerViewActions.<RecipeViewHolder<Recipe>>actionOnItemAtPosition(2, click()));

        onView(withId(R.id.fl_ingredients)).check(matches((isDisplayed())));
     }

     @Test
     public void hasRecipeNutellaPie() {

     }

    @Test
    public void clickRecipeRecyclerViewListItem() {
        // find the view
        onView(withId(R.id.rv_recipes)).check(matches(withText("hello")));

        // perform the action on the view
        // check if the view does what you expect
        assert (true);
    }

    public class NetworkIdlingResource implements IdlingResource {

        @Override
        public String getName() {
            return null;
        }

        @Override
        public boolean isIdleNow() {
            return false;
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback callback) {

        }
    }
}
