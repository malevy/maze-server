package net.malevy.mazeserver.http.dtos;

import java.net.URI;

public class CollectionDto extends LinkCollection {
    private URI href;

    public CollectionDto(URI href) {
        this.href = href;
    }

    public URI getHref() {
        return href;
    }
}
