package net.malevy.mazeserver.http.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.net.URI;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
