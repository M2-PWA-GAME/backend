package com.ynov.master.mobile.game.medieval.warfare.dto;

import com.ynov.master.mobile.game.medieval.warfare.model.*;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class GameResponseDTO {

    String id;

    GameStatus status;

    String name;

    Integer maxPlayers;

    List<String> users;

    List<RoundResponseDTO> rounds;

    List<WeaponResponseDTO> weapons;

    Map map;

    HashMap<String,String> turnOrder;

}
