package com.dreamgames.backendengineeringcasestudy.moderation;

import com.dreamgames.backendengineeringcasestudy.model.GroupParticipant;
import com.dreamgames.backendengineeringcasestudy.model.Tournament;
import com.dreamgames.backendengineeringcasestudy.model.TournamentGroup;
import com.dreamgames.backendengineeringcasestudy.service.GroupParticipantService;
import com.dreamgames.backendengineeringcasestudy.service.TournamentGroupService;
import com.dreamgames.backendengineeringcasestudy.service.TournamentService;
import com.dreamgames.backendengineeringcasestudy.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TournamentOrganizer {

    @Autowired
    private UserService userService ;

    @Autowired
    private GroupParticipantService groupParticipantService ;

    @Autowired
    private TournamentGroupService tournamentGroupService ;

    @Autowired
    private TournamentService tournamentService;

    @Getter
    @Setter
    private Tournament currentTournament ;

    @PostConstruct
    public void init() {
        currentTournament = tournamentService.getCurrentActiveTournament();
    }

    public synchronized void createTournament() {
        Tournament tournament = new Tournament();
        tournament.setTournamentDate(Calendar.getInstance().getTime());
        tournamentService.save(tournament);
        setCurrentTournament(tournament);
    }

    public synchronized void finishTournament(){
        if (currentTournament == null || !currentTournament.isActive()) {
            throw new IllegalStateException("Şu anda aktif bir turnuva yok.");
        }
        distributeRewards();
        currentTournament = tournamentService.getCurrentActiveTournament();
        currentTournament.setActive(false);
        tournamentService.save(currentTournament);
    }

    @Async
    private void distributeRewards() {

        List<TournamentGroup> playedGroups = tournamentGroupService.getGroups(currentTournament.getId());

        for(TournamentGroup tg : playedGroups){

            List<GroupParticipant> participants = tg.getGroupParticipants();

            participants.sort((gp1,gp2)-> Integer.compare(gp2.getScore(),gp1.getScore()));

            userService.saveFirstPlaceReward(participants.get(0).getUser());
            userService.saveSecondPlaceReward(participants.get(1).getUser());

        }

    }


    public synchronized ResponseEntity<HashMap<String, Object>> acceptEntryRequest(Long userId) {

        if(currentTournament == null || !currentTournament.isActive() ){
            HashMap<String, Object> response = returnResponse("message", "Turnuva aktif değil.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }


        if (!groupParticipantService.checkParticipation(userId)){


            int check = userService.checkCoinLevelAndRewardClaim(userId);

            if( check == -1 ){
                HashMap<String, Object> response = returnResponse("message", "Kullanıcı bulunamadı.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }else if( check == 0 ){
                HashMap<String, Object> response = returnResponse("message", "Yetersiz coin ya da level.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else if ( check == 2 ) {
                HashMap<String, Object> response = returnResponse("message", "Alınmamış ödüller var.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }



            TournamentGroup availableGroup = tournamentGroupService.findAnAvailableGroup(userId,currentTournament);

            GroupParticipant newGroupParticipant = new GroupParticipant();
            newGroupParticipant.setUser(userService.getUser(userId));
            newGroupParticipant.setTournamentGroup(availableGroup);
            newGroupParticipant.setScore(0);

            GroupParticipant savedGP = groupParticipantService.saveGroupParticipant(newGroupParticipant);


            List<HashMap<String, Object>> leaderboard = tournamentGroupService.getGroupLeaderboardOnJoin(availableGroup,savedGP);


            HashMap<String, Object> response = returnResponse("Group Leaderboard", leaderboard);
            return ResponseEntity.ok(response);

        }else{

            HashMap<String, Object> response = returnResponse("message", "Katılım önceden sağlanmıştır.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        }


    }

    @Async
    public HashMap<String,Object> returnResponse(String message , Object object){
        HashMap<String, Object> response = new HashMap<>();
        response.put(message,object);
        return response;
    }



}
