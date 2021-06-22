package com.ynov.master.mobile.game.medieval.warfare.dto;

import com.ynov.master.mobile.game.medieval.warfare.model.Action;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

@Data
public class TurnResponseDTO {

    @BsonProperty("player_id")
    private String playerId;

    @BsonProperty("actions")
    List<ActionResponseDTO> actions;

    @BsonProperty("players_states")
    List<PlayerStateResponserDTO> playersStates;

}
