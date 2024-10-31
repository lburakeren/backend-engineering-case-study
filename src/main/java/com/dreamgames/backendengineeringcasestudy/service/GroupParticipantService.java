package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.model.GroupParticipant;
import com.dreamgames.backendengineeringcasestudy.model.TournamentGroup;
import com.dreamgames.backendengineeringcasestudy.repository.GroupParticipantRepository;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupParticipantService {

    @Autowired
    private GroupParticipantRepository groupParticipantRepository;

    @Async
    public GroupParticipant saveGroupParticipant(GroupParticipant newGroupParticipant) {
        return groupParticipantRepository.save(newGroupParticipant);
    }

    @Synchronized
    public boolean incrementScore(Long userId)  {
        Optional<GroupParticipant> groupParticipant = groupParticipantRepository.findByUserId(userId);
        if(groupParticipant.isPresent() && groupParticipant.get().getTournamentGroup().isReady()){
            GroupParticipant foundGP = groupParticipant.get();
            foundGP.setScore(foundGP.getScore()+1);
            groupParticipantRepository.save(foundGP);
            return true;
        } else {
            return false;
        }
    }

    @Async
    public int getGroupRank(Long userId) {

        Optional<GroupParticipant> gp = groupParticipantRepository.findByUserId(userId);

        if (gp.isPresent() && gp.get().getTournamentGroup().isReady()) {
            GroupParticipant foundGP = gp.get();
            TournamentGroup tournamentGroup = foundGP.getTournamentGroup();

            List<GroupParticipant> participants = new ArrayList<>(tournamentGroup.getGroupParticipants());
            participants.sort((gp1, gp2) -> Integer.compare(gp2.getScore(), gp1.getScore()));

            int index = participants.indexOf(foundGP);
            return index + 1 ;
        }
        return -1 ;

    }

    @Async
    public Optional<GroupParticipant> getGroupParticipant(Long userId) {
        return groupParticipantRepository.findByUserId(userId);
    }

    @Async
    public boolean checkParticipation(Long userId) {
        return groupParticipantRepository.existsByUserId(userId);
    }
}
