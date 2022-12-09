package net.malevy.mazeserver.domain;

import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Maze {
    private Cell[][] cells; // height by width
    final private Size size;
    private String id = UUID.randomUUID().toString();

    final Map<String, Cell> ByIdIndex = new HashMap<>();

    public Maze(Size size) {
        Assert.notNull(size, "must provide the size of the maze");
        this.size = size;
        initializeCells();
        setBorders();
        setNeighbors();
    }

    public String getId() {
        return id;
    }

    public Cell getEntrance() {
        return this.cells[0][0];
    }

    private void setNeighbors() {
        final int maxHeightOffset = this.size.getHeight()-1;
        final int maxWidthOffset = this.size.getWidth()-1;
        for(int row = 0; row <= maxHeightOffset; row++) {
            for(int col = 0; col <= maxWidthOffset; col++) {
                Cell cell = this.cells[row][col];
                if (!cell.hasBoarderToThe(Directions.NORTH) && row > 0) cell.addNeighbor(cells[row-1][col], Directions.NORTH);
                if (!cell.hasBoarderToThe(Directions.EAST) && col < maxWidthOffset) cell.addNeighbor(cells[row][ col+1], Directions.EAST);
                if (!cell.hasBoarderToThe(Directions.SOUTH) && row < maxHeightOffset) cell.addNeighbor(cells[row+1][col], Directions.SOUTH);
                if (!cell.hasBoarderToThe(Directions.WEST) && col > 0) cell.addNeighbor(cells[row][col-1], Directions.WEST);
            }
        }
    }

    public Cell getRandomCell() {
        final Random generator = new Random();
        return cells[generator.nextInt(this.size.getHeight())][generator.nextInt(this.size.getWidth())];
    }

    public Cell getCellById(String id) {
        Assert.hasText(id, "must provide the id");
        return this.ByIdIndex.get(id);
    }

    private void setBorders() {
        // set the north and south borders
        final int northBorderOffset = 0;
        final int southBorderOffset = this.size.getHeight()-1;
        for(int col = 0; col < this.size.getWidth(); col++) {
            this.cells[northBorderOffset][col].placeBorderToThe(Directions.NORTH);
            this.cells[southBorderOffset][col].placeBorderToThe(Directions.SOUTH);
        }

        final int westBorderOffset = 0;
        final int eastBorderOffset = this.size.getWidth()-1;
        for(int row=0; row < this.size.getHeight();row++) {
            this.cells[row][westBorderOffset].placeBorderToThe(Directions.WEST);
            this.cells[row][eastBorderOffset].placeBorderToThe(Directions.EAST);
        }

        // lower-right corner
        this.cells[southBorderOffset][eastBorderOffset].placeBorderToThe(Directions.EXIT);
    }

    private void initializeCells() {
        this.cells = new Cell[this.size.getHeight()][this.size.getWidth()];
        for(int y = 0; y < this.size.getHeight(); y++) {
            for(int x = 0; x < this.size.getWidth(); x++) {
                Cell cell = new Cell();
                this.cells[y][x] = cell;
                this.ByIdIndex.put(cell.getId(), cell);
            }
        }
    }

}
