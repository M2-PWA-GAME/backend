package com.ynov.master.mobile.game.medieval.warfare.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SeedMapDataDTO {

    @ApiModelProperty(position = 0)
    private Integer xMax;

    @ApiModelProperty(position = 1)
    private Integer yMax;

    @ApiModelProperty(position = 2)
    private Integer seed;
}
