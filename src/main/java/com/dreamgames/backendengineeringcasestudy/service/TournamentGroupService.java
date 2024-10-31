package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.model.GroupParticipant;
import com.dreamgames.backendengineeringcasestudy.model.Tournament;
import com.dreamgames.backendengineeringcasestudy.model.TournamentGroup;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.TournamentGroupRepository;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentGroupService {

    @Autowired
    private TournamentGroupRepository tournamentGroupRepository;

    @Autowired
    private UserService userService ;

    public TournamentGroup findAnAvailableGroup(Long userId , Tournament tournament) {

        User user = userService.getUser(userId);

        List<TournamentGroup> tournamentGroups = tournamentGroupRepository.findAvailableGroups();

        for(TournamentGroup tournamentGroup : tournamentGroups){

            boolean countryExists = tournamentGroup.getGroupParticipants().stream()
                    .anyMatch(GroupParticipant -> GroupParticipant.getUser().getCountry().equals(user.getCountry()));

            if(!countryExists){
                if( tournamentGroup.getGroupParticipants().size() == 4){
                    tournamentGroup.setReady(true);
                }
                return tournamentGroup;
            }

        }

        TournamentGroup newTournamentGroup = new TournamentGroup();
        newTournamentGroup.setTournament(tournament);
        return tournamentGroupRepository.save(newTournamentGroup);

    }


    public List<HashMap<String,Object>> getGroupLeaderboard(Long groupId) {
        Optional<TournamentGroup> optionalGroup = tournamentGroupRepository.findById(groupId);
        List<HashMap<String, Object>> leaderboard = new ArrayList<>();

        if (optionalGroup.isPresent()) {
            TournamentGroup tournamentGroup = optionalGroup.get();
            List<GroupParticipant> participants = new ArrayList<>(tournamentGroup.getGroupParticipants());
            participants.sort((gp1, gp2) -> Integer.compare(gp2.getScore(), gp1.getScore()));

            for (GroupParticipant participant : participants) {
                HashMap<String, Object> entry = new HashMap<>();
                entry.put("userId", participant.getUser().getId());
                entry.put("username", participant.getUser().getUserName());
                entry.put("country", participant.getUser().getCountry());
                entry.put("score", participant.getScore());
                leaderboard.add(entry);
            }
        }
        return leaderboard;
    }


    public List<HashMap<String,Object>> getGroupLeaderboardOnJoin(TournamentGroup tournamentGroup , GroupParticipant savedGP){
        List<GroupParticipant> gplist = new ArrayList<>(tournamentGroup.getGroupParticipants());
        gplist.add(savedGP);
        gplist.sort((gp1, gp2) -> Integer.compare(gp2.getScore(), gp1.getScore()));
        List<HashMap<String, Object>> leaderboard = new ArrayList<>();
        for (GroupParticipant participant : gplist) {
            HashMap<String, Object> entry = new HashMap<>();
            entry.put("userId", participant.getUser().getId());
            entry.put("username", participant.getUser().getUserName());
            entry.put("country", participant.getUser().getCountry());
            entry.put("score", participant.getScore());
            leaderboard.add(entry);
        }
        return  leaderboard;
    }


    public List<TournamentGroup> getGroups(Long tournamentId) {
        return tournamentGroupRepository.findReadyGroupsByTournamentId(tournamentId);
    }
}
