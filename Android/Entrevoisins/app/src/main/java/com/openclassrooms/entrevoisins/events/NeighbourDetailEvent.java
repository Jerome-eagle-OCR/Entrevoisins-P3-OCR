package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * Event fired when a user clicks on a Neighbour
 */
public class NeighbourDetailEvent {

    /**
     * Neighbour to show details
     */
    public Neighbour neighbour;

    /**
     * Constructor.
     *
     * @param neighbour
     */
    public NeighbourDetailEvent(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}
