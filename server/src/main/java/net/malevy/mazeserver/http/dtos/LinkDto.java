package net.malevy.mazeserver.http.dtos;

import java.net.URI;

public class LinkDto {
    private final URI href;
    private final String rel;
    private final String name;

    public LinkDto(String name, URI href, String rel) {
        this.href = href;
        this.rel = rel;
        this.name = name;
    }

    public LinkDto(URI href, String rel) {
        this(null, href, rel);
    }

    public URI getHref() {
        return href;
    }

    public String getRel() {
        return rel;
    }

    public String getName() {
        return name;
    }
}
