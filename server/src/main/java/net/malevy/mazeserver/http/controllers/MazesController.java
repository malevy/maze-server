package net.malevy.mazeserver.http.controllers;

import net.malevy.mazeserver.data.MazeRepository;
import net.malevy.mazeserver.domain.Cell;
import net.malevy.mazeserver.domain.Maze;
import net.malevy.mazeserver.http.Constants;
import net.malevy.mazeserver.http.ResponseFactory;
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

        Maze[] mazes = this.mazeRepository.getAll();
        return ResponseFactory.fromMazeList(mazes, uriBuilder);
    }

    @GetMapping("/{id}")
    public MazeDto fetchById(@PathVariable String id, UriComponentsBuilder uriBuilder) {
        Maze maze = this.mazeRepository.getById(id);
        if (maze == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "unknown maze");
        }

        return ResponseFactory.ForMaze(maze, uriBuilder);
    }

    @GetMapping("/{mazeid}/cells/{cellid}")
    public MazeDto fetchCell(@PathVariable String mazeid, @PathVariable String cellid, UriComponentsBuilder uriBuilder) {
        Maze maze = this.mazeRepository.getById(mazeid);
        if (maze == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "unknown maze");
        }

        Cell cell = maze.getCellById(cellid);
        return ResponseFactory.ForCell(mazeid, cell, uriBuilder);

    }

}
