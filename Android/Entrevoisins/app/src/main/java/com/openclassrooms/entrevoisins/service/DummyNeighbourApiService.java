package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


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
     *
     * @return list
     */
    @Override
    public List<Neighbour> getFavoriteNeighbours() {

        List<Neighbour> favoriteNeighbours = new ArrayList<>();
        for (int i = 0; i < neighbours.size(); i++) {
            if (neighbours.get(i).getIsFavorite()) {
                favoriteNeighbours.add(neighbours.get(i));
            }
        }
        //Could be used instead but requires Nougat at least while app allows Lollipop as minimum :
        //List<Neighbour> favoriteNeighbours = neighbours.stream().filter(neighbour -> neighbour.getFavorite() == true).collect(Collectors.toList());
        return favoriteNeighbours;
    }

    /**
     * Add neighbour in favorite list
     *
     * @param neighbour
     */
    @Override
    public void addNeighbourToFavorites(Neighbour neighbour) {
        neighbour.setIsFavorite(true);
    }

    /**
     * Remove neighbour from favorite list
     *
     * @param neighbour
     */
    @Override
    public void removeNeighbourFromFavorites(Neighbour neighbour) {
        neighbour.setIsFavorite(false);
    }
}
