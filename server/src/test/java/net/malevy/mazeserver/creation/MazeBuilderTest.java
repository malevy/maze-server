package net.malevy.mazeserver.creation;

import net.malevy.mazeserver.domain.Cell;
import net.malevy.mazeserver.domain.Directions;
import net.malevy.mazeserver.domain.Maze;
import net.malevy.mazeserver.domain.Size;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazeBuilderTest {

    @Test
    public void allCellsAreVisited() {

        final Maze maze = MazeBuilder.build(new Size(2,2));
        final Cell start = maze.getEntrance();
        assertFalse(start.hasAllWalls());
        assertFalse(start.getNeighbors().get(Directions.EAST).hasAllWalls());
        assertFalse(start.getNeighbors().get(Directions.SOUTH).hasAllWalls());
        assertFalse(start.getNeighbors().get(Directions.SOUTH).getNeighbors().get(Directions.EAST).hasAllWalls());

    }


}