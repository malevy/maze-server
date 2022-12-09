package net.malevy.mazeserver.http.dtos;

import java.net.URI;

public class ItemDto {
    private URI href;
    private LinkDto linkDto;

    public ItemDto(URI href, LinkDto linkDto) {
        this.href = href;
        this.linkDto = linkDto;
    }

    public URI getHref() {
        return href;
    }

    public LinkDto getLink() {
        return linkDto;
    }
}
