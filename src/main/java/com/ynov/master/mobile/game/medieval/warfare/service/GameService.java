package com.ynov.master.mobile.game.medieval.warfare.service;

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

@Slf4j
@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationService notificationHandler;

    public void joinGameWithCode(String joinCode, User user) throws Exception {
        Game game = gameRepository.findGameById(joinCode);
        if (game.getUsers().contains(user.getId().toString())) {
            throw new CustomException("You already joined the game", HttpStatus.UNAUTHORIZED);
        }
        this.userJoinGame(game, user);
    }

    public Game getGame(String gameId) {
        Game game = gameRepository.findGameById(gameId);
        if( game == null){
            throw new CustomException("Game with id " + gameId + " not found",HttpStatus.NOT_FOUND);
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

            notificationHandler.gameStarted(game.getUsers(),game.getId().toString());
            notificationHandler.yourTurnNotification(game.getTurnOrder().get("0"),game.getId().toString());
        }

        try{
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
}
