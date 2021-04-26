package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertThrows;
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
    public void getFavoriteNeighboursWithSuccess() {
        //Given : new service (@Before) and the two last neighbours are favorites
        //When : get favorite neighbours _using getFavoriteNeighbours()_
        //Then : result retrieved equals expected list containing the two last neighbours only
        int neighboursListSize = service.getNeighbours().size();
        service.getNeighbours().get(neighboursListSize - 1).setIsFavorite(true);
        service.getNeighbours().get(neighboursListSize - 2).setIsFavorite(true);
        List<Neighbour> expectedFavoriteNeighbours = service.getNeighbours().subList(neighboursListSize - 2, neighboursListSize);
        assertEquals(service.getFavoriteNeighbours(), expectedFavoriteNeighbours);
    }

    @Test
    public void addFavoriteNeighbourWithSuccess() {
        //Given : add first  and second neighbours _using addFavoriteNeighbour()_
        //When : retrieve favorite neighbours _using previously validated getFavoriteNeighbours()_
        //Then : result contains the two added neighbours
        service.addNeighbourToFavorites(service.getNeighbours().get(0));
        service.addNeighbourToFavorites(service.getNeighbours().get(1));
        List<Neighbour> expectedFavoriteNeighbours = service.getNeighbours().subList(0, 2);
        assertTrue(service.getFavoriteNeighbours().containsAll(expectedFavoriteNeighbours));
    }

    @Test
    public void removeFavoriteNeighbourWithSuccess() {
        //Given : first neighbour added in favorites _using previously validated addFavoriteNeighbour()_
        //When : remove neighbour from favorites _using removeFavoriteNeighbour()_
        //Then : list retrieved _using previously validated getFavoriteNeighbours()_ does not contain removed neighbour
        service.addNeighbourToFavorites(service.getNeighbours().get(0));
        service.removeNeighbourFromFavorites(service.getNeighbours().get(0));
        assertFalse(service.getFavoriteNeighbours().contains(service.getNeighbours().get(0)));
    }
}
