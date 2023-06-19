package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.IMazeElements;

public class Wall implements IMazeElements {

    @Override
    public char getSymbol() {
        return 'x';
    }
}
