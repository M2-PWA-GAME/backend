package com.ynov.master.mobile.game.medieval.warfare.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SigninDataDTO {

    @ApiModelProperty(position = 0)
    private String email;

    @ApiModelProperty(position = 1)
    private String password;
}
