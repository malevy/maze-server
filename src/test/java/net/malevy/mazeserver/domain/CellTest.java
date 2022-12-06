package net.malevy.mazeserver.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    public void newCellHasAllWallsSet() {
        final Cell cell = new Cell();
        assertTrue(cell.hasAllWalls(), "should have all walls");
    }

    @Test
    void neighborWithWallIsNotAccessible() {
        final Cell neighbor = new Cell();
        neighbor.removeWallToThe(Directions.SOUTH);
        final Cell cell = new Cell();
        cell.removeWallToThe(Directions.NORTH);
        cell.addNeighbor(neighbor, Directions.NORTH);
        assertEquals(1, cell.getAccessibleNeighbors().size(), "should be one neighbor");
    }

    @Test
    void neighborWithoutWallIsAccessible() {
        final Cell neighbor = new Cell();
        neighbor.removeWallToThe(Directions.SOUTH);
        final Cell cell = new Cell();
        cell.removeWallToThe(Directions.NORTH);
        cell.addNeighbor(neighbor, Directions.NORTH);
        assertEquals(1, cell.getAccessibleNeighbors().size(), "should be one neighbor");
    }
}