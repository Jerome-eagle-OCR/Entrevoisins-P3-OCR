package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
    private List<Neighbour> favoriteNeighbours = new ArrayList<>();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     *
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    /**
     * Get list of favorite neighbours
     * @return list
     */
    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        return favoriteNeighbours;
    }

    /**
     * Add neighbour in favorite list
     * @param selectedNeighbour
     */
    @Override
    public void addFavoriteNeighbour(Neighbour selectedNeighbour) {
        favoriteNeighbours.add(selectedNeighbour);
    }

    /**
     * Remove neighbour from favorite list
     * @param selectedNeighbour
     */
    @Override
    public void removeFavoriteNeighbour(Neighbour selectedNeighbour) {
        favoriteNeighbours.remove(selectedNeighbour);
    }
}
