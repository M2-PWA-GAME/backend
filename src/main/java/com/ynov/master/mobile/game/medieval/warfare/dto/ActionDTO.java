package com.ynov.master.mobile.game.medieval.warfare.dto;


import com.ynov.master.mobile.game.medieval.warfare.model.ActionType;
import com.ynov.master.mobile.game.medieval.warfare.model.Position;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActionDTO {

    @ApiModelProperty(position = 0)
    ActionType actionType;

    @ApiModelProperty(position = 1)
    Position from;

    @ApiModelProperty(position = 2)
    Position to;
}
