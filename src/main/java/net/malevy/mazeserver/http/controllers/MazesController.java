package net.malevy.mazeserver.http.controllers;

import net.malevy.mazeserver.data.MazeRepository;
import net.malevy.mazeserver.domain.Cell;
import net.malevy.mazeserver.domain.Maze;
import net.malevy.mazeserver.http.dtos.CollectionDto;
import net.malevy.mazeserver.http.dtos.ItemDto;
import net.malevy.mazeserver.http.dtos.LinkDto;
import net.malevy.mazeserver.http.dtos.MazeDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URI;

@RestController
@RequestMapping("/mazes")
public class MazesController {

    final private MazeRepository mazeRepository;

    public MazesController(MazeRepository mazeRepository) {
        this.mazeRepository = mazeRepository;
    }

    @GetMapping
    public MazeDto fetchAll(UriComponentsBuilder uriBuilder) {

        UriComponentsBuilder builderForRoot = uriBuilder.pathSegment("mazes");
        CollectionDto collectionDto = new CollectionDto(builderForRoot.build().toUri());
        Maze[] mazes = this.mazeRepository.getAll();
        for (Maze maze : mazes) {
            UriComponentsBuilder builderForMaze = builderForRoot.cloneBuilder();
            URI mazeUrl = builderForMaze.pathSegment(maze.getId()).build().toUri();
            collectionDto.addLink(new LinkDto(mazeUrl, Constants.Rels.MAZE));
        }
        return new MazeDto(collectionDto);
    }

    @GetMapping("/{id}")
    public MazeDto fetchById(@PathVariable String id, UriComponentsBuilder uriBuilder) {
        Maze maze = this.mazeRepository.getById(id);
        if (maze == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "unknown maze");
        }

        final Cell entrance = maze.getEntrance();

        UriComponentsBuilder builderForMaze = uriBuilder.pathSegment("mazes", maze.getId());
        UriComponentsBuilder builderForLink = builderForMaze.cloneBuilder().pathSegment("cell", entrance.getId());

        final LinkDto mazeLink = new LinkDto(
                builderForLink.build().toUri(),
                Constants.Rels.START);
        final ItemDto item = new ItemDto(builderForMaze.build().toUri(), mazeLink);

        return new MazeDto(item);
    }

}
