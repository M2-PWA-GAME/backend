package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Round {

    Integer index;

    List<Turn> turns = new ArrayList();

    RoundState state = RoundState.NEW;

    public Round() {
    }

    public void addTurn(Turn turn) {
        if (state == RoundState.NEW) {
            state = RoundState.PLAYING;
        }

        this.turns.add(turn);
    }


    public Turn lastTurn() {
        if(turns.size() == 0) {
            turns.add(new Turn());
        }
        return turns.get(turns.size() - 1);
    }

}
