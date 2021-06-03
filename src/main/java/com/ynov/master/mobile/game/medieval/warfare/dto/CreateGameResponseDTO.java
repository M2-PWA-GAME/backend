package com.ynov.master.mobile.game.medieval.warfare.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;


@Data
public class CreateGameResponseDTO {
    @ApiModelProperty(position = 0)
    private String id;

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2, value = "join_url")
    private String joinUrl;


    public CreateGameResponseDTO(String id, String name, String joinUrl) {
        this.id = id;
        this.name = name;
        this.joinUrl = joinUrl;
    }
}
