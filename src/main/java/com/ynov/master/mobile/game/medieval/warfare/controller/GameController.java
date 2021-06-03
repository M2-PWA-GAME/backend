package com.ynov.master.mobile.game.medieval.warfare.controller;

import com.ynov.master.mobile.game.medieval.warfare.dto.CreateGameDataDTO;
import com.ynov.master.mobile.game.medieval.warfare.dto.CreateGameResponseDTO;
import com.ynov.master.mobile.game.medieval.warfare.dto.GameActiveResponseDTO;
import com.ynov.master.mobile.game.medieval.warfare.exception.CustomException;
import com.ynov.master.mobile.game.medieval.warfare.model.Game;
import com.ynov.master.mobile.game.medieval.warfare.model.User;
import com.ynov.master.mobile.game.medieval.warfare.service.GameService;
import com.ynov.master.mobile.game.medieval.warfare.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/games")
@Api(tags = "games")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @GetMapping("/join/{code}")
    @ApiOperation(value = "Join a new game")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Something went wrong")})
    public ResponseEntity<Void> joinGame(
            HttpServletRequest req,
            @ApiParam("Game code") @PathVariable("code") String code
    ) throws Exception {
        User user = userService.whoami(req);
        gameService.joinGameWithCode(code, user);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/")
    @ApiOperation(value = "Create a new game")
    @ApiResponses(
            value = {@ApiResponse(code = 400, message = "Something went wrong")}
    )
    public CreateGameResponseDTO createNewGame(
            HttpServletRequest request,
            @ApiParam("Game creation datas") @RequestBody CreateGameDataDTO gameData
    ) throws Exception {
        try {
            User user = userService.whoami(request);
            Game newGame = gameService.createNewGame(gameData.getName(), gameData.getMaxPlayers());
            gameService.userJoinGame(newGame, user);
            return new CreateGameResponseDTO(
                    newGame.getId().toString(),
                    newGame.getName(),
                    request.getRequestURL().toString() + "join/" + newGame.getId().toString()
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @GetMapping("/active/{code}")
    @ApiOperation(value = "Check if the game is enable to join")
    @ApiResponses(
            value = {@ApiResponse(code = 400, message = "Something went wrong")}
    )
    public GameActiveResponseDTO isGameActive(@ApiParam("Game code") @PathVariable("code") String code)
            throws Exception {
        Game game = gameService.getGame(code);
        if (game == null)
            throw new CustomException("Game not found", HttpStatus.NOT_FOUND);
        return new GameActiveResponseDTO(game.getId().toString(), game.getStatus());
    }

}
