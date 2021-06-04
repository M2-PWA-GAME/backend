package com.ynov.master.mobile.game.medieval.warfare.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreateGameDataDTO {

    @ApiModelProperty(position = 0)
    private String name;

    @ApiModelProperty(position = 1)
    private Integer maxPlayers;

    @ApiModelProperty(position = 1)
    private Integer xMax;

    @ApiModelProperty(position = 1)
    private Integer yMax;

    @ApiModelProperty(position = 1)
    private Integer seed;

}
