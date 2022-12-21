package net.malevy.mazeserver.http;

import net.malevy.mazeserver.domain.Cell;
import net.malevy.mazeserver.domain.Directions;
import net.malevy.mazeserver.domain.Maze;
import net.malevy.mazeserver.http.dtos.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public abstract class ResponseFactory {

    public static MazeDto fromMazeList(Maze[] mazes, UriComponentsBuilder uriBuilder) {
        UriComponentsBuilder builderForRoot = uriBuilder.pathSegment("mazes");
        CollectionDto collectionDto = new CollectionDto(builderForRoot.build().toUri());
        for (Maze maze : mazes) {
            collectionDto.addLink(new LinkDto(maze.getName(), buildMazeUrl(maze.getId(), uriBuilder), Constants.Rels.MAZE));
        }
        return new MazeDto(collectionDto);
    }

    public static MazeDto ForMaze(Maze maze, UriComponentsBuilder uriBuilder) {
        final Cell entrance = maze.getEntrance();

        final LinkDto mazeLink = new LinkDto(
                buildCellUrl(maze.getId(), entrance.getId(), uriBuilder),
                Constants.Rels.START);
        final ItemDto item = new ItemDto(buildMazeUrl(maze.getId(), uriBuilder), mazeLink);

        return new MazeDto(item);

    }

    public static MazeDto ForCell(String mazeId, Cell cell, UriComponentsBuilder uriBuilder) {
        final CellDto cellDto = new CellDto(buildCellUrl(mazeId, cell.getId(), uriBuilder));
        if (cell.hasExit()) {
            LinkDto exit = new LinkDto("exit", null, Directions.EXIT.toString());
            cellDto.addLink(exit);
        } else {
            for (Directions direction : cell.getAccessibleNeighbors().keySet()) {
                Cell neighbor = cell.getNeighbors().get(direction);
                LinkDto link = new LinkDto(buildCellUrl(mazeId, neighbor.getId(), uriBuilder), directionToRel(direction));
                cellDto.addLink(link);
            }
        }
        return new MazeDto(cellDto);
    }

    public static URI buildMazeUrl(String id, UriComponentsBuilder uriBuilder) {
        UriComponentsBuilder clonedUriBuilder = uriBuilder.cloneBuilder();
        clonedUriBuilder.replacePath("");
        return clonedUriBuilder.pathSegment(Constants.PathSegments.MAZES, id).build().toUri();
    }

    public static URI buildCellUrl(String mazeId, String cellId, UriComponentsBuilder uriBuilder) {
        UriComponentsBuilder clonedUriBuilder = uriBuilder.cloneBuilder();
        clonedUriBuilder.replacePath("");
        return clonedUriBuilder
                .pathSegment(Constants.PathSegments.MAZES, mazeId)
                .pathSegment(Constants.PathSegments.CELLS, cellId)
                .build().toUri();
    }

    public static String directionToRel(Directions direction) {
        switch (direction) {
            case NORTH:
                return Constants.Rels.NORTH;
            case SOUTH:
                return Constants.Rels.SOUTH;
            case EAST:
                return Constants.Rels.EAST;
            case WEST:
                return Constants.Rels.WEST;
            case EXIT:
                return Constants.Rels.EXIT;
            default:
                throw new IllegalArgumentException("unmapped value for Directions: " + direction);
        }
    }

}
