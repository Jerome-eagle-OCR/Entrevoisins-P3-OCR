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
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours(); //Create favorite list using method to test
        favoriteNeighbours.add(DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(0)); //Add first neighbour of dummy list in favorite list (using List .add method)
        List<Neighbour> expectedFavoriteNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.subList(0, 1); //Create expected favorite list with only first neighbour of dummy list
        assertEquals(service.getFavoriteNeighbours(), expectedFavoriteNeighbours); //Assert retrieved favorite list using method to test equals expected list
    }

    @Test
    public void addFavoriteNeighbourWithSuccess() {
        service.addFavoriteNeighbour(DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(0)); //Add first neighbour of dummy list in favorite list using method to test
        service.addFavoriteNeighbour(DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(1)); //Add second neighbour of dummy list in favorite list using method to test
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours(); //Retrieve favorite list in favoriteNeighbours list using previously validated method
        List<Neighbour> expectedFavoriteNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.subList(0, 2); //Create expected favorite list with only 2 firsts neighbours of dummy list
        assertTrue(favoriteNeighbours.containsAll(expectedFavoriteNeighbours)); //Assert lists are equal
    }

    @Test
    public void removeFavoriteNeighbourWithSuccess() {
        assertThrows(IndexOutOfBoundsException.class, () -> service.getFavoriteNeighbours().get(0));  //Assert list is not null and is empty
        service.addFavoriteNeighbour(DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(0)); //Add first neighbour of dummy list in favorite list using previously validated method
        service.removeFavoriteNeighbour(DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(0)); //Remove same neighbour using method to test
        assertThrows(IndexOutOfBoundsException.class, () -> service.getFavoriteNeighbours().get(0)); //Assert that list is empty again
    }
}
