package com.example.nebo.bakingapp;

import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.nebo.bakingapp.data.Recipe;
import com.example.nebo.bakingapp.viewholder.RecipeViewHolder;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class BakingActivityTest {
    @Rule public ActivityTestRule<BakingActivity> mActivityTestRule =
            new ActivityTestRule<BakingActivity>(BakingActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingDBResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void hasRecipes() {
        // RecipeActivity should now be viewable.
        onView(withId(R.id.ll_recipe)).check(doesNotExist());

        // check to see if the recycler view is already displayed.
        onView(withId(R.id.fl_recipes)).check(matches(isDisplayed()));
        onView(atRecyclerViewPosition(allOf(withText(R.string.recipe_one)), 0)).check(matches(withText(R.string.recipe_one)));


        // .perform(RecyclerViewActions.<RecipeViewHolder<Recipe>>scrollToPosition(0)).;



        // onView(withId(R.id.fl_recipes)).+
        // onView(withId(R.id.fl_recipes)).perform(RecyclerViewActions.<RecipeViewHolder<Recipe>>actionOnItemAtPosition(2, click()));

        // onView(withId(R.id.fl_ingredients)).check(matches((isDisplayed())));
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

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

    @NonNull
    private static Matcher<View> atRecyclerViewPosition(final Matcher<View> parentMatcher, final int position) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
