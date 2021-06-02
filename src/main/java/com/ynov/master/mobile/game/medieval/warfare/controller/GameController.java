package com.ynov.master.mobile.game.medieval.warfare.controller;

import com.ynov.master.mobile.game.medieval.warfare.dto.ActionDTO;
import com.ynov.master.mobile.game.medieval.warfare.model.User;
import com.ynov.master.mobile.game.medieval.warfare.repository.UserRepository;
import com.ynov.master.mobile.game.medieval.warfare.service.GameService;
import com.ynov.master.mobile.game.medieval.warfare.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserRepository userRepository;


    @Autowired
    private UserService userService;


    @GetMapping("/join/{code}")
    @ApiOperation(value = "Join a new game")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
    })
    public String joinGame(HttpServletRequest req, @ApiParam("Game code") @PathVariable("code") String code) throws Exception {
        User user = userService.whoami(req);
        System.out.println("ICI");
        //gameService.join(code);
        return "Oui";
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
