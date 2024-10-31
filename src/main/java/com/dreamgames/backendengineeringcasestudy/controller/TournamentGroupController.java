package com.dreamgames.backendengineeringcasestudy.controller;

import com.dreamgames.backendengineeringcasestudy.service.TournamentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class TournamentGroupController {

    @Autowired
    private TournamentGroupService tournamentGroupService;



    @GetMapping("/getGroupLeaderboard/{groupId}")
    public ResponseEntity<HashMap<String, Object>> getGroupLeaderboardRequest(@PathVariable Long groupId) {
        List<HashMap<String, Object>> leaderboard = tournamentGroupService.getGroupLeaderboard(groupId);

        HashMap<String, Object> response = new HashMap<>();
        if (leaderboard.isEmpty()) {
            response.put("message", "Grup bulunamadı veya liderlik tablosu boş.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            response.put("Group Leaderboard", leaderboard);
            return ResponseEntity.ok(response);
        }
    }



}
