package net.malevy.mazeserver.http.controllers;

import net.malevy.mazeserver.domain.Player;
import net.malevy.mazeserver.http.dtos.MovementDto;
import net.malevy.mazeserver.notifications.CellEnterEvent;
import net.malevy.mazeserver.notifications.CellExitEvent;
import net.malevy.mazeserver.notifications.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@CrossOrigin
@RequestMapping("/players")
public class PlayersController {

    private final Publisher publisher;

    public PlayersController(Publisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Player registerPlayer() {
        final Player player = new Player();
        return player;
    }

    @PostMapping("/{playerId}/movements")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void acceptMovement(@PathVariable String playerId, @RequestBody @Valid MovementDto movement) {

        String mazePath = null;
        try {
            URI mazeUri = new URI(movement.mazeId);
            mazePath = mazeUri.getPath();
        } catch (URISyntaxException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "maze ID is not a well formed URL");
        }

        if (StringUtils.hasText(movement.fromCell)) {
            try {
                final URI uri = new URI(movement.fromCell);
                final String path = uri.getPath();
                final CellExitEvent exitEvent = new CellExitEvent(playerId, mazePath, path);
                this.publisher.post(mazePath, exitEvent);
            } catch (URISyntaxException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'fromCell' is not a well formed URL");
            }
        }
        if (StringUtils.hasText(movement.toCell)) {
            try {
                final URI uri = new URI(movement.toCell);
                final String path = uri.getPath();
                final CellEnterEvent enterEvent = new CellEnterEvent(playerId, mazePath, movement.toCell);
                this.publisher.post(mazePath, enterEvent);
            } catch (URISyntaxException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'toCell' is not a well formed URL");
            }
        }
    }

}
