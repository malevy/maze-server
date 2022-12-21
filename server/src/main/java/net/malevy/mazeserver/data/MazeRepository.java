package net.malevy.mazeserver.data;

import net.malevy.mazeserver.creation.MazeBuilder;
import net.malevy.mazeserver.domain.Maze;
import net.malevy.mazeserver.domain.Size;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MazeRepository {
    final private Map<String, Maze> mazes = new HashMap<>();

    public MazeRepository() {
        Maze maze = MazeBuilder.build(new Size(20,20));
        mazes.put(maze.getId(), maze);
        maze = MazeBuilder.build(new Size(2,2));
        mazes.put(maze.getId(), maze);
    }

    public Maze getById(String id) {
        Assert.hasText(id, "must provide the ID");
        return this.mazes.get(id);
    }

    public Maze[] getAll() {
        return this.mazes.values().toArray(new Maze[0]);
    }
}
