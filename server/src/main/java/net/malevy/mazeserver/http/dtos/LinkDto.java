package net.malevy.mazeserver.http.dtos;

import java.net.URI;

public class LinkDto {
    private URI href;
    private String rel;

    public LinkDto(URI href, String rel) {
        this.href = href;
        this.rel = rel;
    }

    public URI getHref() {
        return href;
    }

    public String getRel() {
        return rel;
    }
}
