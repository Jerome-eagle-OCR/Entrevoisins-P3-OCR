package com.openclassrooms.entrevoisins.neighbour_list;

import android.view.View;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourFragment;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private final int ITEMS_COUNT = 12;
    private final int FAVORITE_ITEMS_COUNT = 3;
    private final int TEST_NEIGHBOUR_1 = 0;
    private final int TEST_NEIGHBOUR_2 = 6;
    private final int TEST_NEIGHBOUR_3 = 8;


    private ListNeighbourActivity mActivity;
    private NeighbourApiService service;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        service = DI.getNewInstanceApiService();
    }

    /**
     * We ensure that our recyclerview is displaying at least one item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(getViewByContentDescription(NeighbourFragment.ALL_NEIGHBOURS_PAGE))
                .check(matches(isDisplayed()));
        onView(getViewByContentDescription(NeighbourFragment.ALL_NEIGHBOURS_PAGE))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(getViewByContentDescription(NeighbourFragment.ALL_NEIGHBOURS_PAGE))
                .check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(getViewByContentDescription(NeighbourFragment.ALL_NEIGHBOURS_PAGE))
                .perform(RecyclerViewActions.actionOnItemAtPosition(TEST_NEIGHBOUR_3 + 1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(getViewByContentDescription(NeighbourFragment.ALL_NEIGHBOURS_PAGE))
                .check(withItemCount(ITEMS_COUNT - 1));
    }

    /**
     * When we click an item, the neighbour details page is displayed
     */
    @Test
    public void myNeighbourList_clickOnNeighbour_shouldDisplayNeighbourDetails() {
        //Given : We choose a neighbour in the list to see his/her details
        onView(getViewByContentDescription(NeighbourFragment.ALL_NEIGHBOURS_PAGE))
                .check(matches(isDisplayed()));
        //When : We perform a click anywhere on the item but the delete button
        onView(getViewByContentDescription(NeighbourFragment.ALL_NEIGHBOURS_PAGE))
                .perform(RecyclerViewActions.actionOnItemAtPosition(TEST_NEIGHBOUR_1, click()));
        //Then : The neighbour details page displays
        onView(withId(R.id.neighbour_details)).check(matches(isDisplayed()));
    }

    /**
     * The neighbour details page display the selected neighbour's actual name
     */
    @Test
    public void myNeighbourDetailsView_shouldDisplayNeighbourName() {
        //Given : We pick a neighbour item in the list to see his/her details
        onView(getViewByContentDescription(NeighbourFragment.ALL_NEIGHBOURS_PAGE))
                .perform(RecyclerViewActions.actionOnItemAtPosition(TEST_NEIGHBOUR_1, click()));
        //When : Neighbour details view is displayed
        onView(withId(R.id.neighbour_details)).check(matches(isDisplayed()));
        //Then : Neighbour's name is properly filled
        onView(withId(R.id.tb_neighbour_name)).check(matches(withText(service.getNeighbours().get(TEST_NEIGHBOUR_1).getName())));
    }

    /**
     * When we delete an item in favorite list, the item is no more shown
     */
    @Test
    public void myFavoriteNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the last item in favorite list that counts 3
        service.getNeighbours().forEach(neighbour -> service.removeNeighbourFromFavorites(neighbour));
        onView(getViewByContentDescription(NeighbourFragment.ALL_NEIGHBOURS_PAGE))
                .perform(RecyclerViewActions.actionOnItemAtPosition(TEST_NEIGHBOUR_1, click()));
        onView(withId(R.id.fav_neighbour_btn)).perform(click());
        pressBack();
        onView(getViewByContentDescription(NeighbourFragment.ALL_NEIGHBOURS_PAGE))
                .perform(RecyclerViewActions.actionOnItemAtPosition(TEST_NEIGHBOUR_2, click()));
        onView(withId(R.id.fav_neighbour_btn)).perform(click());
        pressBack();
        onView(getViewByContentDescription(NeighbourFragment.ALL_NEIGHBOURS_PAGE))
                .perform(RecyclerViewActions.actionOnItemAtPosition(TEST_NEIGHBOUR_3 + 1, click()));
        onView(withId(R.id.fav_neighbour_btn)).perform(click());
        pressBack();
        onView(getViewByContentDescription(NeighbourFragment.ALL_NEIGHBOURS_PAGE))
                .perform(swipeLeft());
        onView(getViewByContentDescription(NeighbourFragment.FAVORITE_NEIGHBOURS_PAGE))
                .check(withItemCount(FAVORITE_ITEMS_COUNT));
        // When : perform a click on a delete icon
        onView(getViewByContentDescription(NeighbourFragment.FAVORITE_NEIGHBOURS_PAGE))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, new DeleteViewAction()));
        // Then : the number of elements is 2 and they contain the 2 left favorite neighbours' names
        onView(getViewByContentDescription(NeighbourFragment.FAVORITE_NEIGHBOURS_PAGE))
                .check(withItemCount(FAVORITE_ITEMS_COUNT - 1))
                .check(matches(hasDescendant(withText(service.getNeighbours().get(TEST_NEIGHBOUR_1).getName()))))
                .check(matches(hasDescendant(withText(service.getNeighbours().get(TEST_NEIGHBOUR_2).getName()))));
    }

    /**
     * When we display favorite list it only shows favorite neighbours
     */
    @Test
    public void myFavoriteNeighboursList_containsOnlyFavorites() {
        // Given : We set 3 neighbours as favorites
        service.getNeighbours().forEach(neighbour -> service.removeNeighbourFromFavorites(neighbour));
        onView(getViewByContentDescription(NeighbourFragment.ALL_NEIGHBOURS_PAGE))
                .perform(actionOnItemAtPosition(TEST_NEIGHBOUR_1, click()));
        onView(withId(R.id.fav_neighbour_btn)).perform(click());
        pressBack();
        onView(getViewByContentDescription(NeighbourFragment.ALL_NEIGHBOURS_PAGE))
                .perform(actionOnItemAtPosition(TEST_NEIGHBOUR_2, click()));
        onView(withId(R.id.fav_neighbour_btn)).perform(click());
        pressBack();
        onView(getViewByContentDescription(NeighbourFragment.ALL_NEIGHBOURS_PAGE))
                .perform(actionOnItemAtPosition(TEST_NEIGHBOUR_3, click()));
        onView(withId(R.id.fav_neighbour_btn)).perform(click());
        pressBack();
        // When : we display favorite list
        onView(getViewByContentDescription(NeighbourFragment.ALL_NEIGHBOURS_PAGE))
                .perform(swipeLeft());
        // The number of elements is only 3 and they contain the 3 favorite neighbours' names
        onView(getViewByContentDescription(NeighbourFragment.FAVORITE_NEIGHBOURS_PAGE))
                .check(withItemCount(FAVORITE_ITEMS_COUNT))
                .check(matches(hasDescendant(withText(service.getNeighbours().get(TEST_NEIGHBOUR_1).getName()))))
                .check(matches(hasDescendant(withText(service.getNeighbours().get(TEST_NEIGHBOUR_2).getName()))))
                .check(matches(hasDescendant(withText(service.getNeighbours().get(TEST_NEIGHBOUR_3).getName()))));
    }

    private static Matcher<View> getViewByContentDescription(int contentDescription) {
        return allOf(withId(R.id.list_neighbours), withContentDescription(String.valueOf(contentDescription)));
    }
}