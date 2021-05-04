package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void getNeighbourFromIdWithSuccess() {
        //Given : neighbour id
        //When : get neighbour from id _using getNeighbourFromId()_
        //Then : retrieved neighbour is the expected one
        long neighbourId = service.getNeighbours().get(0).getId();
        assertEquals(service.getNeighbours().get(0), service.getNeighbourFromId(neighbourId));
    }

    @Test
    public void getFavoriteNeighboursWithSuccess() {
        //Given : no favorites then the two last neighbours set as favorites
        //When : get favorite neighbours _using getFavoriteNeighbours()_
        //Then : result retrieved equals expected list containing the two last neighbours only
        service.getNeighbours().forEach(neighbour -> neighbour.setIsFavorite(false));
        int neighboursListSize = service.getNeighbours().size();
        service.getNeighbours().get(neighboursListSize - 1).setIsFavorite(true);
        service.getNeighbours().get(neighboursListSize - 2).setIsFavorite(true);
        List<Neighbour> expectedFavoriteNeighbours = service.getNeighbours().subList(neighboursListSize - 2, neighboursListSize);
        assertEquals(expectedFavoriteNeighbours, service.getFavoriteNeighbours());
    }

    @Test
    public void addFavoriteNeighbourWithSuccess() {
        //Given : no favorites then add first  and second neighbours _using addFavoriteNeighbour()_
        //When : retrieve favorite neighbours _using previously validated getFavoriteNeighbours()_
        //Then : result contains the two added neighbours
        service.getNeighbours().forEach(neighbour -> neighbour.setIsFavorite(false));
        service.addNeighbourToFavorites(service.getNeighbours().get(0));
        service.addNeighbourToFavorites(service.getNeighbours().get(1));
        List<Neighbour> expectedFavoriteNeighbours = service.getNeighbours().subList(0, 2);
        assertTrue(service.getFavoriteNeighbours().containsAll(expectedFavoriteNeighbours));
    }

    @Test
    public void removeFavoriteNeighbourWithSuccess() {
        //Given : 2 neighbours are in favorites _using previously validated addFavoriteNeighbour()_
        //When : remove first neighbour from favorites _using removeFavoriteNeighbour()_
        //Then : list retrieved _using previously validated getFavoriteNeighbours()_ does not contain removed neighbour
        service.addNeighbourToFavorites(service.getNeighbours().get(0));
        service.addNeighbourToFavorites(service.getNeighbours().get(1));
        service.removeNeighbourFromFavorites(service.getNeighbours().get(0));
        assertFalse(service.getFavoriteNeighbours().contains(service.getNeighbours().get(0)));
    }
}
