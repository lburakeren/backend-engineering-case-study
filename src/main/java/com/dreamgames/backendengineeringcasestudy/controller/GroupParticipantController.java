package com.dreamgames.backendengineeringcasestudy.controller;

import com.dreamgames.backendengineeringcasestudy.service.GroupParticipantService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupParticipantController {

    @Autowired
    private GroupParticipantService groupParticipantService;

    @GetMapping("/getGroupRank/{userId}")
    public ResponseEntity<String> getGroupRankRequest(@PathVariable Long userId) {

        int rank = groupParticipantService.getGroupRank(userId);

        if ( rank == -1 ) {
            String response = "Hata! Rank bilgisi bulunumadı." ;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(String.valueOf(rank));

    }

}
