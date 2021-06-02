package com.ynov.master.mobile.game.medieval.warfare.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserGamesResponseDTO {

    @ApiModelProperty(position = 0)
    String id;

    @ApiModelProperty(position = 1)
    List<String> games;
}
