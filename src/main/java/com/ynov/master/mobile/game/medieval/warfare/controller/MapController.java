package com.ynov.master.mobile.game.medieval.warfare.controller;

import com.ynov.master.mobile.game.medieval.warfare.dto.RandomMapDataDTO;
import com.ynov.master.mobile.game.medieval.warfare.dto.SeedMapDataDTO;
import com.ynov.master.mobile.game.medieval.warfare.exception.CustomException;
import com.ynov.master.mobile.game.medieval.warfare.model.Map;
import com.ynov.master.mobile.game.medieval.warfare.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maps")
public class MapController {

    @Autowired
    private MapService mapService;

    @PostMapping("/random")
    public ResponseEntity<Map> generateRandomMap(@RequestBody RandomMapDataDTO input) {
        if (isNotInRange(input.getXMax(), input.getYMax())) {
            throw new CustomException("Max map size : 100 x 100", HttpStatus.BAD_REQUEST);
        }
        Map map = mapService.generateRandomMap(input.getXMax(), input.getYMax());
        return ResponseEntity.status(HttpStatus.CREATED).body(map);
    }

    @PostMapping("/seed")
    public ResponseEntity<Map> generateRandomMap(@RequestBody SeedMapDataDTO input) {
        if (isNotInRange(input.getXMax(), input.getYMax())) {
            throw new CustomException("Max map size : 100 x 100", HttpStatus.BAD_REQUEST);
        }
        if ((input.getSeed() > 30000) || (input.getSeed() < 9000)) {
            throw new CustomException("Map seed need to be between 9000 and 30000", HttpStatus.BAD_REQUEST);
        }
        Map map = mapService.generateMapFromSeed(input.getXMax(), input.getYMax(), input.getSeed());
        return ResponseEntity.status(HttpStatus.CREATED).body(map);
    }

    private boolean isNotInRange(int x, int y) {
        final int max = 100;
        return x < 0 || x > max || y < 0 || y > max;
    }

}
