package com.dreamgames.backendengineeringcasestudy.moderation;

import com.dreamgames.backendengineeringcasestudy.controller.UserController;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import lombok.Data;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@Data
public class TournamentEntryController {

    @Autowired
    private TournamentOrganizer tournamentOrganizer;

    @PostMapping("/enterTournament/{userId}")
    public ResponseEntity<HashMap<String,Object>> enterTournament(@PathVariable Long userId ){
        return tournamentOrganizer.acceptEntryRequest(userId);
    }

    @GetMapping("/createTournament")
    public void startTournament(){
        tournamentOrganizer.createTournament();
    }

    @GetMapping("/finishTournament")
    public void endTournament(){
        tournamentOrganizer.finishTournament();
    }



}
