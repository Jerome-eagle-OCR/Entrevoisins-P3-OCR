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
import static org.junit.Assert.assertThrows;

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
        /**
         * First possible test
         */
        //Given : new service (@Before)
        //When : get favorite neighbours _using getFavoriteNeighbours()_
        //Then : an empty list is properly returned (non null)
        assertThrows(IndexOutOfBoundsException.class, () -> service.getFavoriteNeighbours().get(0));

        /**
         * Second possible test
         */
        //Given : new service (@Before)
        //When : create favoriteNeighbours list _using getFavoriteNeighbours()_ and add last dummy neighbour in the list with "legacy" method,
        //Then : retrieved favorite list _using getFavoriteNeighbours()_ equals expected list
        int dummyListSize = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.size();
        DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(dummyListSize - 1).setFavori(true);
        List<Neighbour> expectedFavoriteNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.subList(dummyListSize - 1, dummyListSize);
        List<Neighbour> testList = service.getFavoriteNeighbours();
        testList = service.getFavoriteNeighbours();
        assertEquals(service.getFavoriteNeighbours(), expectedFavoriteNeighbours);

        /**
         * Third possible test is a combination of the two first possible tests
         */
    }

    @Test
    public void addFavoriteNeighbourWithSuccess() {
        //Given : new service (@Before)
        //When : add dummy neighbours (0) two times _using addFavoriteNeighbour()_
        //Then : retrieved favorite list _using previously validated getFavoriteNeighbours()_ equals expected list
        service.addFavoriteNeighbour(DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(0));
        List<Neighbour> expectedFavoriteNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.subList(0, 1);
        assertEquals(service.getFavoriteNeighbours(), expectedFavoriteNeighbours);
    }

    @Test
    public void removeFavoriteNeighbourWithSuccess() {
        //Given : new service (@Before) and dummy neighbour (0) added in favorites _using previously validated addFavoriteNeighbour()_
        //When : remove dummy neighbour (0) _using removeFavoriteNeighbour()_
        //Then : list retrieved _using previously validated getFavoriteNeighbours()_ is empty
        service.addFavoriteNeighbour(DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(0));
        service.removeFavoriteNeighbour(DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> service.getFavoriteNeighbours().get(0));
    }
}
