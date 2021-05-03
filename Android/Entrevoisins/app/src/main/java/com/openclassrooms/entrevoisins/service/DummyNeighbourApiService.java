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
     * Get neighbour from his/her id
     *
     * @param neighbourId id of neighbour wanted to be retrieved
     * @return neighbour retrieved in list or null if not
     */
    @Override
    public Neighbour getNeighbourFromId(Long neighbourId) {
        for (Neighbour neighbour : neighbours) {
            if (neighbour.getId() == neighbourId) {
                return neighbour;
            }
        }
        return null;
    }

    /**
     * Get a list of favorite neighbours
     *
     * @return list
     */
    @Override
    public List<Neighbour> getFavoriteNeighbours() {

        List<Neighbour> favoriteNeighbours = new ArrayList<>();
        for (Neighbour neighbour : neighbours) {
            if (neighbour.getIsFavorite()) {
                favoriteNeighbours.add(neighbour);
            }
        }
        //Could be used instead but requires Nougat at least while app allows Lollipop as minimum :
        //List<Neighbour> favoriteNeighbours = neighbours.stream().filter(neighbour -> neighbour.getFavorite() == true).collect(Collectors.toList());
        return favoriteNeighbours;
    }

    /**
     * Add neighbour in favorites
     *
     * @param neighbour the neighbour to set as favorite
     */
    @Override
    public void addNeighbourToFavorites(Neighbour neighbour) {
        neighbour.setIsFavorite(true);
    }

    /**
     * Remove neighbour from favorites
     *
     * @param neighbour the neighbour to unset as favorite
     */
    @Override
    public void removeNeighbourFromFavorites(Neighbour neighbour) {
        neighbour.setIsFavorite(false);
    }
}
