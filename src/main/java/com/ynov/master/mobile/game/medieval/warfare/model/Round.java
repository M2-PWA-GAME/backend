package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;

import java.util.List;

@Data
public class Round {

    Integer index;

    List<Turn> turns;

    RoundState state = RoundState.NEW;

    public Round() {
    }



    public Turn getLastTurn() {
        return turns.get(turns.size() - 1);
    }

}
