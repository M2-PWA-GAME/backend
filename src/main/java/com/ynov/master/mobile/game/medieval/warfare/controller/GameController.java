package com.ynov.master.mobile.game.medieval.warfare.controller;

import com.ynov.master.mobile.game.medieval.warfare.dto.CreateGameDataDTO;
import com.ynov.master.mobile.game.medieval.warfare.dto.CreateGameResponseDTO;
import com.ynov.master.mobile.game.medieval.warfare.dto.GameActiveResponseDTO;
import com.ynov.master.mobile.game.medieval.warfare.dto.GameResponseDTO;
import com.ynov.master.mobile.game.medieval.warfare.exception.CustomException;
import com.ynov.master.mobile.game.medieval.warfare.model.Game;
import com.ynov.master.mobile.game.medieval.warfare.model.Map;
import com.ynov.master.mobile.game.medieval.warfare.model.User;
import com.ynov.master.mobile.game.medieval.warfare.service.GameService;
import com.ynov.master.mobile.game.medieval.warfare.service.MapService;
import com.ynov.master.mobile.game.medieval.warfare.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private MapService mapService;

    @Autowired
    ModelMapper mapper;

    @GetMapping("/{code}")
    @ApiOperation(value = "Get game data without map")
    public GameResponseDTO getFullGameDataWithoutMap(HttpServletRequest req, @ApiParam("Game code") @PathVariable("code") String code) {
        User user = userService.whoami(req);
        Game game = gameService.getGame(code);

        if (!game.getUsers().contains(user.getId().toString())) {
            throw new CustomException("you are not allowed to access a game if you are not part of it", HttpStatus.UNAUTHORIZED);
       }

        return mapper.map(game, GameResponseDTO.class);
    }

    @GetMapping("/{code}/map")
    @ApiOperation(value = "Get game's map")
    public Map getFullGameData(HttpServletRequest req, @ApiParam("Game code") @PathVariable("code") String code) {
        User user = userService.whoami(req);
        Game game = gameService.getGame(code);

        if (!game.getUsers().contains(user.getId().toString())) {
            throw new CustomException("you are not allowed to access a game if you are not part of it", HttpStatus.UNAUTHORIZED);
        }

        return game.getMap();
    }

    @GetMapping("/join/{code}")
    @ApiOperation(value = "Join a new game")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Something went wrong")})
    public ResponseEntity<Void> joinGame(
            HttpServletRequest req,
            @ApiParam("Game code") @PathVariable("code") String code
    ) throws Exception {
        User user = userService.whoami(req);
        gameService.joinGameWithCode(code, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create a new game")
    @ApiResponses(
            value = {@ApiResponse(code = 400, message = "Something went wrong")}
    )
    public CreateGameResponseDTO createNewGame(
            HttpServletRequest request,
            @ApiParam("Game creation datas") @RequestBody CreateGameDataDTO gameData
    ) throws Exception {

        User user = userService.whoami(request);
        Map map = mapService.generateMap(gameData.getXMax(), gameData.getYMax(), gameData.getSeed());

        Game newGame = gameService.createNewGame(gameData.getName(), gameData.getMaxPlayers(), user, map);

        return new CreateGameResponseDTO(
                newGame.getId().toString(),
                newGame.getName(),
                request.getRequestURL().toString() + "/join/" + newGame.getId().toString()
        );
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
