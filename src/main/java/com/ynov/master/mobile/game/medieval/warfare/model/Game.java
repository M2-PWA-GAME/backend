package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Comparator;
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
    HashMap<String, String> turnOrder;

    public void addUser(User user) {
        this.users.add(user.getId().toString());
    }

    public void initGame() {

        List<String> usersList = users;

        // Random Order
        Collections.shuffle(usersList);
        HashMap<String, String> order = new HashMap<>();
        IntStream.range(0, this.getMaxPlayers()).forEach(index ->
                order.put(String.valueOf(index), usersList.get(index))
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
        initialRound.setState(RoundState.FINISH);

        this.setRounds(Collections.singletonList(initialRound));
    }


    public List<Round> getSortedRounds() {
        return this.getRounds().stream().sorted(Comparator.comparingInt(Round::getIndex)).collect(Collectors.toList());
    }

    public Round getLastRound() {
        return this.getSortedRounds().get(this.getSortedRounds().size() - 1);
    }


    public Round addRound() {
        this.rounds.add(new Round());
        return this.getLastRound();
    }

    public Boolean hasWinner() {
        List<PlayerState> test = this.getLastRound().getLastTurn().getPlayersStates().stream().filter(playerState -> playerState.getHealth() > 0)
                .collect(Collectors.toList());
        return test.size() > 1 ? false : true;
    }

}
