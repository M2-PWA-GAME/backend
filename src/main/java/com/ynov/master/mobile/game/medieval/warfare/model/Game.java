package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class Game {

    @BsonProperty("_id")
    private ObjectId id;

    @BsonProperty("status")
    GameStatus status;

    @BsonProperty("name")
    String name;

    @BsonProperty("maxPlayers")
    Integer maxPlayers;

    @BsonProperty("users")
    List<String> users;

    @BsonProperty("rounds")
    List<Round> rounds;

    @BsonProperty("weapons")
    List<Weapon> weapons;

    @BsonProperty("map")
    Map map;

    @BsonProperty("turnOrder")
    HashMap<Integer, String> turnOrder;

    public void addUser(User user) {
        this.users.add(user.getId().toString());
    }

    public void initGame() {

        List<String> usersList = users;

        // Random Order
        Collections.shuffle(usersList);
        HashMap<Integer, String> order = new HashMap<>();
        IntStream.range(0, this.getMaxPlayers()).forEach(index ->
                order.put(index, usersList.get(index))
        );
        this.setTurnOrder(order);

        // Initial States with player health & weapons
        List<PlayerState> pStates = usersList.stream().map(PlayerState::initialState).collect(Collectors.toList());

        // Initial position
        Random random = new Random();
        List<Position> possiblePosition = this.getMap().getTiles().stream()
                .filter(t -> t.isNavigable)
                .map(Tile::getPosition)
                .collect(Collectors.toList());

        Collections.shuffle(possiblePosition);

        pStates.forEach(s -> {
            int randomInt = random.nextInt(map.getXMax());
            s.setPosition(possiblePosition.get(randomInt));
        });

        Turn initialTurn = new Turn();
        initialTurn.setPlayersStates(pStates);
        initialTurn.setActions(null);
        initialTurn.setPlayerId(null);

        // initial Round ( 1 Round = All player turns )
        Round initialRound = new Round();
        initialRound.setIndex(0);
        initialRound.setTurns(Collections.singletonList(initialTurn));
        initialRound.setState(TurnState.FINISH);

        this.setRounds(Collections.singletonList(initialRound));
    }


    public List<Round> getSortedRounds() {
        return this.getRounds().stream().sorted(Comparator.comparingInt(Round::getIndex)).collect(Collectors.toList());
    }

}
