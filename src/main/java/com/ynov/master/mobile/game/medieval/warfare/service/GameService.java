package com.ynov.master.mobile.game.medieval.warfare.service;

import com.ynov.master.mobile.game.medieval.warfare.dto.ActionDTO;
import com.ynov.master.mobile.game.medieval.warfare.exception.CustomException;
import com.ynov.master.mobile.game.medieval.warfare.model.*;
import com.ynov.master.mobile.game.medieval.warfare.repository.GameRepository;
import com.ynov.master.mobile.game.medieval.warfare.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotifificationService notificationHandler;

    @Autowired
    ModelMapper mapper;

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

    public Game playTurn(User user, ActionDTO action, String gameId) throws Exception {
        Game game = gameRepository.findGameById(gameId);

        if (!this.isPlayerInGame(game, user)) {
            throw new CustomException("You are not in this game", HttpStatus.FORBIDDEN);
        }

        if (game.getStatus() == GameStatus.FINISHED) {
            throw new CustomException("The game is FINISHED", HttpStatus.LOCKED);
        }

        if (!this.isPlayerTurn(game, user)) {
            throw new CustomException("This is not your turn", HttpStatus.LOCKED);
        }


        List<PlayerState> lastPlayerStates = game.lastRound().lastTurn().getPlayersStates();
        PlayerState currentPlayerState = lastPlayerStates
                .stream()
                .filter(state -> state.getId().toString().equals(user.getId().toString()))
                .findFirst()
                .orElse(null);

        if (currentPlayerState == null) {
            throw new CustomException("Something weird happened", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<PlayerState> newStates = this.executeAction(action, currentPlayerState, game.getMap(), game);

        if (game.lastRound().getState() == RoundState.FINISH) {
            Turn newTurn = new Turn();
            newTurn.setPlayerId(user.getId());
            game.addRound().addTurn(newTurn);
        }

        if (!game.lastRound().lastTurn().getPlayerId().toString().equals(user.getId().toString())) {
            Turn newTurn = new Turn();
            newTurn.setPlayerId(user.getId());
            game.lastRound().addTurn(newTurn);
        }
        this.addActionToTurn(game, action);
        game.lastRound().lastTurn().setPlayersStates(newStates);

        if (game.lastRound().lastTurn().getActions().size() == 0 && action.getActionType() == ActionType.PASS) {
            this.addActionToTurn(game, action);
            game.lastRound().lastTurn().setPlayersStates(newStates);
        }

        if (this.getPlayerOrder(game, user.getId().toString()) == game.getUsers().size() - 1 &&
                game.lastRound().lastTurn().getActions().size() == 2) {
            game.lastRound().setState(RoundState.FINISH);
        }

        if (game.hasWinner()) {
            game.lastRound().setState(RoundState.FINISH);
            game.setStatus(GameStatus.FINISHED);
        } else if (game.lastRound().lastTurn().getActions().size() == 2) {
            //TODO notify next player to play
        }
        gameRepository.update(game);
        return game;
    }


    private Boolean isPlayerInGame(Game game, User user) {
        return game.getUsers().contains(user.getId().toString());
    }

    private Boolean isPlayerTurn(Game game, User user) {
        String userId = user.getId().toString();
        Round lastRound = game.lastRound();

        if (isPlayerDead(game, userId)) {
            return false;
        } else if (lastRound.getState() == RoundState.FINISH) {
            String firstUser = game.getTurnOrder().get("0");
            return firstUser.equals(userId);
        } else {

            Turn lastTurn = lastRound.lastTurn();
            if (lastTurn.getPlayerId().toString().equals(userId) && lastTurn.getActions().size() < 2) {
                return true;
            }
            String lastPlayer = this.getLastPlayerWhoPlayed(lastTurn);
            Integer lastPlayerOrder = this.getPlayerOrder(game, lastPlayer);
            Integer currentPlayerOrder = this.getPlayerOrder(game, userId);
            return lastPlayerOrder == currentPlayerOrder - 1;
        }
    }

    private Boolean isPlayerDead(Game game, String userId) {
        PlayerState state = game.lastRound().lastTurn().getPlayersStates()
                .stream()
                .filter(playerState -> playerState.getId().toString().equals(userId))
                .findFirst()
                .orElse(null);
        if (state == null) throw new CustomException("Something weird happened", HttpStatus.INTERNAL_SERVER_ERROR);

        return state.getHealth() <= 0;
    }

    private String getLastPlayerWhoPlayed(Turn lastTurn) {
        return lastTurn.getPlayerId().toString();
    }

    private Integer getPlayerOrder(Game game, String userID) {
        Entry<String, String> order = game.getTurnOrder()
                .entrySet()
                .stream()
                .filter((key) -> userID.equals(key.getValue()))
                .findFirst()
                .orElse(null);

        if (order != null) {
            return Integer.parseInt(order.getKey());
        }
        return null;
    }

    private List<PlayerState> executeAction(ActionDTO action, PlayerState lastState, Map map, Game game) {

        switch (action.getActionType()) {
            case MOVE:
                return makeMove(action, lastState, map, game);
            case HIT:
                return makeAttack(action, lastState, game);
            case PASS:
                return makePass(action, game);
            default:
                return null;

        }
    }


    private List<PlayerState> makePass(ActionDTO action, Game game) {
        List<PlayerState> lastStates = game.lastRound().lastTurn().getPlayersStates();
        return lastStates;
    }

    private List<PlayerState> makeAttack(ActionDTO action, PlayerState lastState, Game game) {
        this.checkLastPosition(lastState, action.getFrom());
        List<PlayerState> lastStates = game.lastRound().lastTurn().getPlayersStates();

        lastStates.forEach((playerState) -> {
            if (playerState.getPosition().equals(action.getTo())) {
                PlayerState state = lastStates.get(lastStates.indexOf(playerState));
                Integer damages = lastState.getWeapon().getDamages();
                if (state.getArmor() <= damages) {
                    state.setHealth(state.getHealth() - (damages - state.getArmor()));
                    state.setArmor(0);
                } else {
                    state.setArmor(state.getArmor() - damages);
                }

            }
        });
        return lastStates;
    }

    private void addActionToTurn(Game game, ActionDTO action) {
        try {
            game.lastRound().lastTurn().addAction(mapper.map(action, Action.class));
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private List<PlayerState> makeMove(ActionDTO action, PlayerState lastState, Map map, Game game) {

        this.checkLastPosition(lastState, action.getFrom());

        Position actionMovement = action.getTo();
        Tile landingTile = map.findTile(actionMovement.getX(), actionMovement.getY());
        if (!landingTile.getIsNavigable()) {
            throw new CustomException("You cant walk there", HttpStatus.BAD_REQUEST);
        }

        List<PlayerState> nextStates = game.lastRound().lastTurn().getPlayersStates().stream().map(PlayerState::clone).collect(Collectors.toList());


        nextStates.stream()
                .filter(playerState -> playerState.getId().toString().equals(lastState.getId().toString()))
                .findFirst().ifPresent(changeState -> changeState.setPosition(action.getTo()));

        //TODO Changer arme si marche sur une case avec

        return nextStates;
    }

    private void checkLastPosition(PlayerState lastState, Position from) {
        Position lastPosition = lastState.getPosition();
        if (!lastPosition.equals(from)) {
            throw new CustomException("Try not to cheat please", HttpStatus.BAD_REQUEST);
        }
    }

}
