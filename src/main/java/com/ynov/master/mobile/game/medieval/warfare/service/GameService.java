package com.ynov.master.mobile.game.medieval.warfare.service;

import com.ynov.master.mobile.game.medieval.warfare.exception.CustomException;
import com.ynov.master.mobile.game.medieval.warfare.model.Game;
import com.ynov.master.mobile.game.medieval.warfare.model.GameStatus;
import com.ynov.master.mobile.game.medieval.warfare.model.User;
import com.ynov.master.mobile.game.medieval.warfare.repository.GameRepository;
import com.ynov.master.mobile.game.medieval.warfare.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;


    public void joinGameWithCode(String joinCode, User user) throws Exception {
        Game game = gameRepository.findGameById(joinCode);
        if (game.getUsers().contains(user.getId().toString())) {
            throw new CustomException("You already joined the game", HttpStatus.UNAUTHORIZED);
        }
        this.userJoinGame(game, user);
    }

    public Game getGame(String gameId) throws Exception {
        return gameRepository.findGameById(gameId);
    }

    public void userJoinGame(Game game, User user) throws Exception {

        if (game.getMaxPlayers() <= game.getUsers().size()) {
            throw new CustomException("Game has max players in it", HttpStatus.GONE);
        }

        game.addUser(user);
        gameRepository.update(game);
        user.addGame(game);
        userRepository.update(user);
    }


    public Game createNewGame(String name, Integer maxPlayers) {
        Game game = new Game();
        game.setId(new ObjectId());
        game.setName(name);
        game.setMaxPlayers(maxPlayers);
        game.setUsers(new ArrayList<>());
        game.setStatus(GameStatus.WAITING);
        gameRepository.save(game);
        return game;
    }
}
