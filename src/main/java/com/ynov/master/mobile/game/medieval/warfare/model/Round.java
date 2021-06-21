package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;

import java.util.List;

@Data
public class Round {

    Integer index;

    List<Turn> turns;

    TurnState state = TurnState.NEW;

    public Round() {
    }

    public void addTurn(Turn turn) {
        if (state == TurnState.NEW) {
            state = TurnState.PLAYING;
        }

        this.turns.add(turn);
    }


    public Turn getLastTurn() {
        return turns.get(turns.size() - 1);
    }

}
