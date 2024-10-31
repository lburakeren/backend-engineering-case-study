package com.dreamgames.backendengineeringcasestudy.repository;

import com.dreamgames.backendengineeringcasestudy.model.Tournament;
import com.dreamgames.backendengineeringcasestudy.model.TournamentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentGroupRepository extends JpaRepository<TournamentGroup,Long> {

    @Query("SELECT g FROM TournamentGroup g WHERE SIZE(g.groupParticipants) < 5")
    List<TournamentGroup> findAvailableGroups();

    @Query("SELECT tg FROM TournamentGroup tg WHERE tg.tournament.id = :tournamentId AND tg.isReady = true")
    List<TournamentGroup> findReadyGroupsByTournamentId(@Param("tournamentId") Long tournamentId);


}
