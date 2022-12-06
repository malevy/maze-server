package net.malevy.mazeserver.http.dtos;

import java.net.URI;

public class CellDto extends LinkCollection {
    private URI href;
    private String rel;

    public CellDto(URI href, String rel) {
        this.href = href;
        this.rel = rel;
    }

}
