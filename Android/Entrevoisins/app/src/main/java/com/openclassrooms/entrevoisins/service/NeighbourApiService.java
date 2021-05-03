package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     *
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     *
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     *
     * @param neighbour
     */
    void createNeighbour(Neighbour neighbour);

    /**
     * Get neighbour from his/her id
     *
     * @param neighbourId id of neighbour wanted to be retrieved
     * @return neighbour retrieved in list or null if not
     */
    Neighbour getNeighbourFromId(Long neighbourId);

    /**
     * Get favorite list
     */
    List<Neighbour> getFavoriteNeighbours();

    /**
     * Add new favorite to favorite neighbours list
     *
     * @param selectedNeighbour neighbour to add in favorite list
     */
    void addNeighbourToFavorites(Neighbour selectedNeighbour);

    /**
     * Remove favorite from favorite neighbours list
     *
     * @param selectedNeighbour neighbour to remove from favorite list
     */
    void removeNeighbourFromFavorites(Neighbour selectedNeighbour);
}
