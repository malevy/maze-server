package net.malevy.mazeserver.notifications;

public class CellEnterEvent {
    public String mazeId;
    public String cellId;
    public String playerId;

    public CellEnterEvent(String playerId, String mazeId, String cellId) {
        this.playerId = playerId;
        this.mazeId = mazeId;
        this.cellId = cellId;
    }
}
