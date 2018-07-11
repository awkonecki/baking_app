package com.example.nebo.bakingapp;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
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

        // check to see if the frame layout that is supposed to contain the recycler view is already displayed.
        onView(withId(R.id.fl_recipes)).check(matches(isDisplayed()));

        // Make sure that each recipe is in the correct position.
        for (int position = 0; position < 4; position++) {
            String recipeName = null;
            int id = 0;

            switch(position) {
                case 0:
                    id = R.string.recipe_one;
                    break;
                case 1:
                    id = R.string.recipe_two;
                    break;
                case 2:
                    id = R.string.recipe_three;
                    break;
                case 3:
                    id = R.string.recipe_four;
                    break;
            }

            recipeName = InstrumentationRegistry.getTargetContext().getResources().getString(id);

            for (int subposition = 0; subposition < 4; subposition++) {


                if (subposition == position) {
                    // Positive case for existence.
                    onView(atPositionOnView(subposition, -1, R.id.rv_recipes)).check(matches(hasDescendant(withText(recipeName))));
                }
                else {
                    // Negative case for not equal
                    onView(atPositionOnView(subposition, -1, R.id.rv_recipes)).check(matches(not(hasDescendant(withText(recipeName)))));
                }
            }
        }
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


    private static Matcher<View> atPositionOnView(final int position, final int targetViewId, final int recyclerViewId) {
        return new TypeSafeMatcher<View>() {
            Resources resources = null;
            View childView = null;

            @Override
            protected boolean matchesSafely(View item) {
                resources = item.getResources();

                if (childView == null) {
                    RecyclerView recyclerView = (RecyclerView) item.getRootView().findViewById(recyclerViewId);

                    if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                        if (viewHolder != null) {
                            childView = viewHolder.itemView;
                        }
                    }
                    else {
                        return false;
                    }
                }

                if (targetViewId == -1) {
                    return item == childView;
                }
                else {
                    View targetView = childView.findViewById(targetViewId);
                    return item == targetView;
                }
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }

    // stackoverflow.com/questions/31394569/how-to-assert-inside-a-recyclerview-in-espresso
    @NonNull
    private static Matcher<View> atRecyclerPosition(@NonNull final Matcher<View> itemMatcher, final int position) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("item at position " + Integer.toString(position) + " is ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                RecyclerView.ViewHolder viewHolder = item.findViewHolderForAdapterPosition(position);

                if (viewHolder != null) {
                    return false;
                }
                else {
                    return itemMatcher.matches(viewHolder.itemView);
                }
            }
        };
    }
}
