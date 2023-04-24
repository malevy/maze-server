package net.malevy.mazeserver.http.dtos;

import javax.validation.constraints.NotBlank;

public class MovementDto {
    public String mazeId;
    public String fromCell;
    public String toCell;
}
