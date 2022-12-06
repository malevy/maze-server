package net.malevy.mazeserver.domain;

import java.util.*;

/**
 * A cell in the maze
 */
public class Cell {
    private EnumSet<Directions> borders = Directions.EMPTY();
    private EnumSet<Directions> walls = Directions.ALL();
    private Map<Directions, Cell> neighbors = new HashMap<>();
    private String id = UUID.randomUUID().toString();

    public boolean hasBoarderToThe(Directions d) {
        return this.borders.contains(d);
    }

    public boolean hasWallToThe(Directions d) {
        return this.walls.contains(d);
    }

    public boolean hasAllWalls() {
        return this.walls.equals(Directions.ALL());
    }

    public boolean hasExit() {
        return this.borders.contains(Directions.EXIT);
    }

    public void placeBorderToThe(Directions d) {
        this.borders.add(d);
    }

    public void removeWallToThe(Directions d) {
        this.walls.remove(d);
    }

    public void addNeighbor(Cell cell, Directions direction) {
        this.neighbors.put(direction, cell);
    }

    public Map<Directions, Cell> getNeighbors() {
        return neighbors;
    }

    public Map<Directions, Cell> getAccessibleNeighbors() {
        Map<Directions, Cell> accessibleNeighbors = new HashMap<>();
        for(Directions direction : neighbors.keySet()) {
            if (!this.hasWallToThe(direction)) accessibleNeighbors.put(direction, neighbors.get(direction));
        }

        return accessibleNeighbors;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "borders=" + borders +
                ", walls=" + walls +
                ", id='" + id + "'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return id.equals(cell.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
