package net.malevy.mazeserver.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {

    @Test
    public void neighborsAreSetCorrectly() {
        final Maze maze = new Maze(new Size(2,2));
        final Cell start = maze.getEntrance();
        assertEquals(2, start.getNeighbors().size());
        assertTrue(start.getNeighbors().containsKey(Directions.EAST));
        assertTrue(start.getNeighbors().containsKey(Directions.SOUTH));

        final Cell cell = start.getNeighbors().get(Directions.EAST);
        assertEquals(2, cell.getNeighbors().size());
        assertTrue(cell.getNeighbors().containsKey(Directions.WEST));
        assertTrue(start.getNeighbors().containsKey(Directions.SOUTH));

    }

    @Test
    public void bordersAreSet() {
        final Maze maze = new Maze(new Size(3,3));

        // row one
        final Cell start = maze.getEntrance();
        assertTrue(start.hasBoarderToThe(Directions.NORTH));
        assertTrue(start.hasBoarderToThe(Directions.WEST));

        Cell cell = start.getNeighbors().get(Directions.EAST);
        assertTrue(cell.hasBoarderToThe(Directions.NORTH));

        cell = cell.getNeighbors().get(Directions.EAST);
        assertTrue(cell.hasBoarderToThe(Directions.NORTH));
        assertTrue(cell.hasBoarderToThe(Directions.EAST));

        // row two
        cell = start.getNeighbors().get(Directions.SOUTH);
        assertTrue(start.hasBoarderToThe(Directions.WEST));

        // middle cell
        cell = cell.getNeighbors().get(Directions.EAST);
        assertFalse(cell.hasBoarderToThe(Directions.NORTH));
        assertFalse(cell.hasBoarderToThe(Directions.EAST));
        assertFalse(cell.hasBoarderToThe(Directions.SOUTH));
        assertFalse(cell.hasBoarderToThe(Directions.WEST));

        cell = cell.getNeighbors().get(Directions.EAST);
        assertTrue(cell.hasBoarderToThe(Directions.EAST));

        // row three
        cell = start.getNeighbors().get(Directions.SOUTH);
        cell = cell.getNeighbors().get(Directions.SOUTH);
        assertTrue(cell.hasBoarderToThe(Directions.WEST));
        assertTrue(cell.hasBoarderToThe(Directions.SOUTH));

        cell = cell.getNeighbors().get(Directions.EAST);
        assertTrue(cell.hasBoarderToThe(Directions.SOUTH));

        cell = cell.getNeighbors().get(Directions.EAST);
        assertTrue(cell.hasBoarderToThe(Directions.SOUTH));
        assertTrue(cell.hasBoarderToThe(Directions.EAST));

    }

    @Test
    public void exitIsSetCorrectly() {
        final Maze maze = new Maze(new Size(1,3));
        Cell cell = maze.getEntrance();
        cell = cell.getNeighbors().get(Directions.EAST);
        cell = cell.getNeighbors().get(Directions.EAST);
        assertTrue(cell.hasExit(), "the exit should be in this cell");
    }

}