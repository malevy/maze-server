package net.malevy.mazeserver.http.controllers;

import net.malevy.mazeserver.data.MazeRepository;
import net.malevy.mazeserver.domain.Cell;
import net.malevy.mazeserver.domain.Maze;
import net.malevy.mazeserver.http.ResponseFactory;
import net.malevy.mazeserver.http.dtos.MazeDto;
import net.malevy.mazeserver.notifications.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/mazes")
@CrossOrigin
public class MazesController {

    final private MazeRepository mazeRepository;
    private final Publisher publisher;

    public MazesController(MazeRepository mazeRepository, Publisher publisher) {
        this.mazeRepository = mazeRepository;
        this.publisher = publisher;
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
        MazeDto dto = ResponseFactory.ForCell(mazeid, cell, uriBuilder);
        return dto;
    }

    @GetMapping("/{mazeid}/notifications")
    public SseEmitter getNotificationsForMaze(@PathVariable String mazeid) {

        // register the path
        return this.publisher.register("/mazes/"+mazeid);
    }

}
