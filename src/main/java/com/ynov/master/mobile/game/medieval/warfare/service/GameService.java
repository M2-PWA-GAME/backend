package com.ynov.master.mobile.game.medieval.warfare.service;

import com.ynov.master.mobile.game.medieval.warfare.dto.ActionDTO;
import com.ynov.master.mobile.game.medieval.warfare.exception.CustomException;
import com.ynov.master.mobile.game.medieval.warfare.model.*;
import com.ynov.master.mobile.game.medieval.warfare.repository.GameRepository;
import com.ynov.master.mobile.game.medieval.warfare.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

@Slf4j
@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotifificationService notificationHandler;

    public void joinGameWithCode(String joinCode, User user) throws Exception {
        Game game = gameRepository.findGameById(joinCode);
        if (this.isPlayerInGame(game, user)) {
            throw new CustomException("You already joined the game", HttpStatus.UNAUTHORIZED);
        }
        this.userJoinGame(game, user);
    }

    public Game getGame(String gameId) {
        Game game = gameRepository.findGameById(gameId);
        if (game == null) {
            throw new CustomException("Game with id " + gameId + " not found", HttpStatus.NOT_FOUND);
        }
        return game;
    }

    public void userJoinGame(Game game, User user) throws Exception {

        if (game.getMaxPlayers() <= game.getUsers().size()) {
            throw new CustomException("Game has max players in it", HttpStatus.GONE);
        }

        game.addUser(user);
        user.addGame(game);
        userRepository.update(user);

        // Si tous les joueurs attendu sont la
        if (game.getUsers().size() == game.getMaxPlayers()) {
            game.initGame();
            game.setStatus(GameStatus.PLAYING);

            notificationHandler.sendNotifications(game.getUsers(), "Player list completed ! Good luck every one.");
            notificationHandler.sendNotification(game.getTurnOrder().get(0), "Your turn, start the game !");
        }

        try {
            gameRepository.update(game);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Game createNewGame(String name, Integer maxPlayers, User user, Map map) throws Exception {

        if (maxPlayers < 2) {
            throw new CustomException("Max player must be greater or equals to 2", HttpStatus.BAD_REQUEST);
        }

        Game game = new Game();

        game.setId(new ObjectId());
        game.setName(name);
        game.setMaxPlayers(maxPlayers);
        game.setMap(map);
        game.setUsers(Collections.singletonList(user.getId().toString()));
        game.setStatus(GameStatus.WAITING);

        user.addGame(game);

        gameRepository.save(game);
        userRepository.update(user);

        return game;
    }

    public Action executeAction(User user, ActionDTO action, String gameId) {
        Game game = gameRepository.findGameById(gameId);
        if (!this.isPlayerInGame(game, user)) {
            throw new CustomException("You are not in this game", HttpStatus.UNAUTHORIZED);
        }

        if (this.isPlayerTurn(game, user)) {
            throw new CustomException("This is not your turn", HttpStatus.UNAUTHORIZED);
        }

        return null;
    }


    private Boolean isPlayerInGame(Game game, User user) {
        return game.getUsers().contains(user.getId().toString());
    }

    private Boolean isPlayerTurn(Game game, User user) {
        List<Round> rounds = game.getSortedRounds();
        String userId = user.getId().toString();
        Round lastRound = rounds.get(rounds.size() - 1);

        if (lastRound.getState() == TurnState.FINISH) {
            String firstUser = game.getTurnOrder().get("0");
            return firstUser != userId ? false : true;
        } else {
            Turn lastTurn = lastRound.getLastTurn();
            String lastPlayer = this.getLastPlayerWhoPlayed(lastTurn);
            Integer lastPlayerOrder = this.getPlayerOrder(game, lastPlayer);
            Integer currentPlayerOrder = this.getPlayerOrder(game, userId);
            return lastPlayerOrder == currentPlayerOrder - 1;
        }
    }

    private String getLastPlayerWhoPlayed(Turn lastTurn) {
        return lastTurn.getPlayerId().toString();
    }

    private Integer getPlayerOrder(Game game, String userID) {
        Entry<Integer, String> test = game.getTurnOrder().entrySet().stream().filter((key) -> userID.equals(key.getValue())).findFirst().orElse(null);
        if (test != null) {
            return test.getKey();
        }
        return null;
    }

}
