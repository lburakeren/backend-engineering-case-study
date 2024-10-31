package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.model.Tournament;
import com.dreamgames.backendengineeringcasestudy.repository.TournamentRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TournamentServiceTest {

    @Mock
    private TournamentRepository tournamentRepository;

    @InjectMocks
    private TournamentService tournamentService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void getCountryLeaderboard() {

        Long tournamentId = 1L ;
        Tournament tournament = new Tournament();
        tournament.setActive(true);
        tournament.setTur_score(5);
        tournament.setFra_score(4);
        tournament.setGer_score(3);
        tournament.setUsa_score(1);
        tournament.setUk_score(6);

        HashMap<String,Object> expectedLeaderboard = new HashMap<>();
        expectedLeaderboard.put("UK SCORE",6);
        expectedLeaderboard.put("TURKEY SCORE",5);
        expectedLeaderboard.put("FRANCE SCORE",4);
        expectedLeaderboard.put("GERMANY SCORE",3);
        expectedLeaderboard.put("USA SCORE",1);

        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));


        HashMap<String,Object> returnedLeaderboard = tournamentService.getCountryLeaderboard(tournamentId);

        assertNotNull(returnedLeaderboard);
        assertEquals(expectedLeaderboard.size(), returnedLeaderboard.size());
        for (Map.Entry<String, Object> entry : expectedLeaderboard.entrySet()) {
            assertTrue(returnedLeaderboard.containsKey(entry.getKey()));
            assertEquals(entry.getValue(), returnedLeaderboard.get(entry.getKey()));
        }

        verify(tournamentRepository).findById(tournamentId);

    }

    @Test
    public void getCurrentActiveTournament() {

        Tournament tournament = new Tournament();
        tournament.setActive(true);

        when(tournamentRepository.findByIsActiveTrue()).thenReturn(Optional.of(tournament));

        Tournament foundTournament = tournamentService.getCurrentActiveTournament();

        assertNotNull(foundTournament);
        assertTrue(foundTournament.isActive());

    }
}