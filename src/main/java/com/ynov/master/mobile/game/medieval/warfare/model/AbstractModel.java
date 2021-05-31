package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Getter;
import org.bson.Document;
import org.bson.types.ObjectId;

public abstract class AbstractModel {

    @Getter
    private final ObjectId id;

    public AbstractModel()
    {
        this.id = new ObjectId();
    }

    public abstract Document toDocument();
}
