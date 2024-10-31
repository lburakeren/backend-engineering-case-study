package com.dreamgames.backendengineeringcasestudy.moderation;

import com.dreamgames.backendengineeringcasestudy.service.TournamentService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Data
public class TournamentScheduler {

    @Autowired
    private TournamentOrganizer tournamentOrganizer ;

    @Scheduled(cron = "1 0 0 * * ?")
    public void startTournament(){
        tournamentOrganizer.createTournament();
    }

    @Scheduled(cron = "59 0 20 * * ?")
    public void endTournament(){
        tournamentOrganizer.finishTournament();
    }

}
