package net.malevy.mazeserver.http.dtos;

import java.net.URI;

public class CellDto extends LinkCollection {
    private URI href;

    public CellDto(URI href) {
        this.href = href;
    }

    public URI getHref() {
        return href;
    }
}
