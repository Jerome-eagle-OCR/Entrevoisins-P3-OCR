package com.openclassrooms.entrevoisins.neighbour_list;

import android.content.ClipData;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

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
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
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
    private static int ITEMS_COUNT = 12;
    private static int FAVORITE_ITEMS_COUNT = 3;

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
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT - 1));
    }

    @Test
    public void myNeighbourList_clickOnNeighbour_shouldDisplayNeighbourDetails() {
        //Given : We choose a neighbour in the list to see his/her details
        //onView(allOf(withId(R.id.container)...  //La liste générale de voisins est bien affichée
        //When : We perform a click anywhere on the item but the delete button
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //Then : The neighbour details page displays
        onView(ViewMatchers.withId(R.id.neighbour_details)).check(matches(isDisplayed()));
    }

    @Test
    public void myNeighbourDetailsView_shouldDisplayNeighbourName() {
        //Given : We pick a neighbour item in the list to see his/her details
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //When : Neighbour details view is displayed
        onView(ViewMatchers.withId(R.id.neighbour_details)).check(matches(isDisplayed()));
        //Then : Neighbour's name is properly filled
        onView(ViewMatchers.withId(R.id.tb_neighbour_name)).check(matches(withText(service.getNeighbours().get(0).getName())));
    }

    @Test
    public void myFavoriteNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2 in favorite list that counts 3 items
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(ViewMatchers.withId(R.id.fav_neighbour_btn)).perform(click());
        pressBack();
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(ViewMatchers.withId(R.id.fav_neighbour_btn)).perform(click());
        pressBack();
        scrollToPosition(8);
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(8, click()));
        onView(ViewMatchers.withId(R.id.fav_neighbour_btn)).perform(click());
        pressBack();
        swipeLeft();
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(FAVORITE_ITEMS_COUNT));
        // When : perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, new DeleteViewAction()));
        // Then : the number of element is 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(FAVORITE_ITEMS_COUNT - 1));
    }

    @Test
    public void myFavoriteNeighboursList_containsOnlyFavorites()() {
        // Given : We set 3 neighbours as favorites
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(ViewMatchers.withId(R.id.fav_neighbour_btn)).perform(click());
        pressBack();
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(ViewMatchers.withId(R.id.fav_neighbour_btn)).perform(click());
        pressBack();
        scrollToPosition(8);
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(8, click()));
        onView(ViewMatchers.withId(R.id.fav_neighbour_btn)).perform(click());
        pressBack();
        // When : we display favorite list
        swipeLeft();
        // The number of elements is only 3 and these 3 are the ones we set as favorites
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(FAVORITE_ITEMS_COUNT));
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewMatchers.withId(R.id.item_list_name).matches(withText(service.getNeighbours().get(0).getName())))));
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewMatchers.withId(R.id.item_list_name).matches(withText(service.getNeighbours().get(3).getName())))));
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewMatchers.withId(R.id.item_list_name).matches(withText(service.getNeighbours().get(8).getName())))));
    }
}