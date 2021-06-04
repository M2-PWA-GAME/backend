package com.ynov.master.mobile.game.medieval.warfare.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreateGameDataDTO {

    @ApiModelProperty(position = 0)
    private String name;

    @ApiModelProperty(position = 1)
    private Integer maxPlayers;

    @ApiModelProperty(position = 2)
    private Integer xMax;

    @ApiModelProperty(position = 3)
    private Integer yMax;

    @ApiModelProperty(position = 4)
    private Integer seed;

}
