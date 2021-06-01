package com.ynov.master.mobile.game.medieval.warfare.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserDataDTO {

    @ApiModelProperty(position = 0)
    private String username;

    @ApiModelProperty(position = 1)
    private String email;

    @ApiModelProperty(position = 2)
    private String password;

}
