package com.ynov.master.mobile.game.medieval.warfare.dto;

import com.ynov.master.mobile.game.medieval.warfare.model.GameStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserGamesResponseDTO {

    @ApiModelProperty()
    String id;

    @ApiModelProperty(position = 1)
    List<UserGamesPreviewResponseDTO> games;
}

