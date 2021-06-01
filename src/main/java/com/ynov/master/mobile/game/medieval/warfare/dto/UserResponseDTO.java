package com.ynov.master.mobile.game.medieval.warfare.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import com.ynov.master.mobile.game.medieval.warfare.model.Role;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class UserResponseDTO {

    @ApiModelProperty(position = 0)
    private ObjectId id;

    @ApiModelProperty(position = 1)
    private String username;

    @ApiModelProperty(position = 2)
    private String email;

    @ApiModelProperty(position = 3)
    List<Role> roles;

}
