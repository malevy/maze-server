package net.malevy.mazeserver.notifications;

public class CellExitEvent {
    public String mazeId;
    public String cellId;
    public String playerId;

    public CellExitEvent(String playerId, String mazeId, String cellId) {
        this.mazeId = mazeId;
        this.cellId = cellId;
        this.playerId = playerId;
    }
}
