package com.ynov.master.mobile.game.medieval.warfare.dto;

import com.ynov.master.mobile.game.medieval.warfare.model.GameStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserGamesPreviewResponseDTO {
    @ApiModelProperty()
    String id;

    @ApiModelProperty(position = 1)
    String name;

    @ApiModelProperty(position = 2)
    GameStatus status;
}
