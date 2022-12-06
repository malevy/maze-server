package net.malevy.mazeserver.creation;

import net.malevy.mazeserver.domain.*;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

public final class MazeBuilder {

    private MazeBuilder() {}

    public static Maze build(Size size) {
        Assert.notNull(size, "must provice a size");

        final Stack<Cell> visitedCells = new Stack<>();
        final Maze maze = new Maze(size);
        final Random random = new Random();
        Cell currentCell = maze.getRandomCell();


        while (true) {
            List<Edge> unvisitedNeighbors = currentCell.getNeighbors().entrySet()
                    .stream()
                    .filter(pair -> pair.getValue().hasAllWalls())
                    .map(pair -> new Edge(pair.getKey(), pair.getValue()))
                    .collect(Collectors.toList());

            Edge next = null;
            if (unvisitedNeighbors.isEmpty()) {
                // if all the neighbors have been visited at least once,
                // back up to the cell we just came from and start a new path
                if (visitedCells.empty()) break; // back at the starting cell
                currentCell = visitedCells.pop();
                continue;
            } else if (unvisitedNeighbors.size() == 1) {
                next = unvisitedNeighbors.get(0);
            } else {
                next = unvisitedNeighbors.get(random.nextInt(unvisitedNeighbors.size()));
            }

            currentCell.removeWallToThe(next.direction);
            next.cell.removeWallToThe(Directions.oppositeOf(next.direction));
            visitedCells.push(currentCell);
            currentCell = next.cell;

        }
        return maze;
    }

    static class Edge {
        public Directions direction;
        public Cell cell;

        public Edge(Directions direction, Cell cell) {
            this.direction = direction;
            this.cell = cell;
        }
    }

}
