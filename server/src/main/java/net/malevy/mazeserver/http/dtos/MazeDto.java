package net.malevy.mazeserver.http.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MazeDto extends LinkCollection {
    private CollectionDto collectionDto;
    private ItemDto itemDto;
    private CellDto cellDto;

    public MazeDto(CollectionDto collectionDto) {
        this.collectionDto = collectionDto;
    }

    public MazeDto(ItemDto itemDto) {
        this.itemDto = itemDto;
    }

    public MazeDto(CellDto cellDto) {
        this.cellDto = cellDto;
    }

    public CollectionDto getCollection() {
        return collectionDto;
    }

    public ItemDto getItem() {
        return itemDto;
    }

    public CellDto getCell() {
        return cellDto;
    }
}
