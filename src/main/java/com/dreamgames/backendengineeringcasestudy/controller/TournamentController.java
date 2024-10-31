package com.dreamgames.backendengineeringcasestudy.controller;

import com.dreamgames.backendengineeringcasestudy.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @GetMapping("/getCountryLeaderboard/{tournamentId}")
    public ResponseEntity<HashMap<String, Object>> getCountryLeaderboard(@PathVariable Long tournamentId){
        LinkedHashMap<String, Object> leaderboard = tournamentService.getCountryLeaderboard(tournamentId);

        HashMap<String, Object> response = new HashMap<>();
        if (leaderboard.isEmpty()) {
            response.put("message", "Turnuva bulunamadı.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            response.put("Country Leaderboard", leaderboard);
            return ResponseEntity.ok(response);
        }
    }

}
