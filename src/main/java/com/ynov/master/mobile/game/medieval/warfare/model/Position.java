package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Objects;

@Data
@NoArgsConstructor
public class Position {
    @BsonProperty("x")
    Integer X;

    @BsonProperty("y")
    Integer Y;

    public Position(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(X, position.X) && Objects.equals(Y, position.Y);
    }
}
