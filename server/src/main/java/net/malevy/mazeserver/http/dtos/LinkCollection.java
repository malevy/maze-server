package net.malevy.mazeserver.http.dtos;

import java.util.ArrayList;
import java.util.List;

public abstract class LinkCollection {
    private List<LinkDto> linkDtos = new ArrayList<>();

    public void addLink(LinkDto linkDto) {
        this.linkDtos.add(linkDto);
    }

    public LinkDto[] getLinks() {
        return linkDtos.toArray(new LinkDto[0]);
    }
}
