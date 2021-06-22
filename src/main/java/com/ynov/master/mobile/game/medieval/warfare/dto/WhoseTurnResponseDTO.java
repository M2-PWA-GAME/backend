package com.ynov.master.mobile.game.medieval.warfare.dto;

import lombok.Data;

@Data
public class WhoseTurnResponseDTO {

    String whoseTurn;

    public WhoseTurnResponseDTO() {
    }

    public WhoseTurnResponseDTO(String whoseTurn) {
        this.whoseTurn = whoseTurn;
    }
}
