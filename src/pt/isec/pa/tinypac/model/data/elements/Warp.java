package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.IMazeElements;


/**
 *Represents a Warp element in a maze.
 *Implements the IMazeElements interface.
 *
 *@author João Santos
 *@version 1.0.0
 */
public class Warp implements IMazeElements {

    @Override
    public char getSymbol() {
        return 'W';
    }
}
