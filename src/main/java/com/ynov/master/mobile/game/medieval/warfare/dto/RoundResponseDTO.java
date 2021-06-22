package com.ynov.master.mobile.game.medieval.warfare.dto;

import com.ynov.master.mobile.game.medieval.warfare.model.RoundState;
import lombok.Data;

import java.util.List;

@Data
public class RoundResponseDTO {
    Integer index;

    List<TurnResponseDTO> turns;

    RoundState state;

}
