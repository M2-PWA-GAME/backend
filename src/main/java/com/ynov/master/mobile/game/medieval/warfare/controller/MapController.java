package com.ynov.master.mobile.game.medieval.warfare.controller;

import com.ynov.master.mobile.game.medieval.warfare.dto.RandomMapDataDTO;
import com.ynov.master.mobile.game.medieval.warfare.dto.SeedMapDataDTO;
import com.ynov.master.mobile.game.medieval.warfare.model.Map;
import com.ynov.master.mobile.game.medieval.warfare.service.MapService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maps")
@Api(tags = "maps")
public class MapController {

    @Autowired
    private MapService mapService;

    @PostMapping("/random")
    public ResponseEntity<Map> generateRandomMap(@RequestBody RandomMapDataDTO input) {
        Map map = mapService.generateRandomMap(input.getXMax(), input.getYMax());
        return ResponseEntity.status(HttpStatus.CREATED).body(map);
    }

    @PostMapping("/seed")
    public ResponseEntity<Map> generateRandomMap(@RequestBody SeedMapDataDTO input) {
        Map map = mapService.generateMapFromSeed(input.getXMax(), input.getYMax(), input.getSeed());
        return ResponseEntity.status(HttpStatus.CREATED).body(map);
    }

}
