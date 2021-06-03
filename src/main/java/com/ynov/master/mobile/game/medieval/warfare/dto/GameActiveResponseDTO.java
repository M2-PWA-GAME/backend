package com.ynov.master.mobile.game.medieval.warfare.dto;

import com.ynov.master.mobile.game.medieval.warfare.model.GameStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GameActiveResponseDTO {


    @ApiModelProperty(position = 0)
    String gameId;

    @ApiModelProperty(position = 0)
    GameStatus status;

    public GameActiveResponseDTO(String gameId, GameStatus status) {
        this.gameId = gameId;
        this.status = status;
    }
}
