package com.ynov.master.mobile.game.medieval.warfare.controller;

import com.ynov.master.mobile.game.medieval.warfare.dto.ActionDTO;
import com.ynov.master.mobile.game.medieval.warfare.service.GameService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/games")
@Api(tags = "games")
public class GameController {


    @Autowired
    private GameService gameService;


    @PostMapping("/join")
    @ApiOperation(value = "Join a new game")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
    })
    public String joinGame(@ApiParam("Game code") @RequestBody String code) throws Exception {
        return gameService.join(code);
    }


    @PostMapping("/{gameId}/play")
    @ApiOperation(value = "Play your turn")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
    })
    public String executeAction(@ApiParam("Game ID") @RequestBody ObjectId gameId,
                                @ApiParam("Action to execute") @RequestBody ActionDTO action) throws Exception {
        //TODO
        throw new Exception("Not implemented");
    }

}
