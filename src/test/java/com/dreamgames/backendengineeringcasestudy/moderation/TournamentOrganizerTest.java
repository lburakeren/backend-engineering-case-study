package com.dreamgames.backendengineeringcasestudy.moderation;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.springframework.http.HttpStatus.*;

import com.dreamgames.backendengineeringcasestudy.model.GroupParticipant;
import com.dreamgames.backendengineeringcasestudy.model.Tournament;
import com.dreamgames.backendengineeringcasestudy.model.TournamentGroup;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.moderation.TournamentOrganizer;
import com.dreamgames.backendengineeringcasestudy.service.GroupParticipantService;
import com.dreamgames.backendengineeringcasestudy.service.TournamentGroupService;
import com.dreamgames.backendengineeringcasestudy.service.TournamentService;
import com.dreamgames.backendengineeringcasestudy.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TournamentOrganizerTest {

    @Mock
    private UserService userService;

    @Mock
    private GroupParticipantService groupParticipantService;

    @Mock
    private TournamentGroupService tournamentGroupService;

    @Mock
    private TournamentService tournamentService;

    @InjectMocks
    private TournamentOrganizer tournamentOrganizer;

    private Tournament currentTournament;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        currentTournament = new Tournament();
        currentTournament.setActive(true);
        tournamentOrganizer.setCurrentTournament(currentTournament);
    }

    @Test
    public void acceptEntryRequest_TournamentIsNotActive() {
        currentTournament.setActive(false);

        ResponseEntity<HashMap<String, Object>> response = tournamentOrganizer.acceptEntryRequest(1L);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals("Turnuva aktif değil.", Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test
    public void acceptEntryRequest_UserNotFound() {
        Long userId = 1L;

        when(groupParticipantService.checkParticipation(userId)).thenReturn(false);
        when(userService.checkCoinLevelAndRewardClaim(userId)).thenReturn(-1);

        ResponseEntity<HashMap<String, Object>> response = tournamentOrganizer.acceptEntryRequest(userId);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals("Kullanıcı bulunamadı.", Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test
    public void acceptEntryRequest_InsufficientCoinsOrLevel() {
        Long userId = 1L;

        when(groupParticipantService.checkParticipation(userId)).thenReturn(false);
        when(userService.checkCoinLevelAndRewardClaim(userId)).thenReturn(0);

        ResponseEntity<HashMap<String, Object>> response = tournamentOrganizer.acceptEntryRequest(userId);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals("Yetersiz coin ya da level.", Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test
    public void acceptEntryRequest_UnclaimedRewards() {
        Long userId = 1L;

        when(groupParticipantService.checkParticipation(userId)).thenReturn(false);
        when(userService.checkCoinLevelAndRewardClaim(userId)).thenReturn(2);

        ResponseEntity<HashMap<String, Object>> response = tournamentOrganizer.acceptEntryRequest(userId);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals("Alınmamış ödüller var.", Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test
    public void acceptEntryRequest_UserSuccessfullyParticipated() {
        Long userId = 1L;
        TournamentGroup availableGroup = new TournamentGroup();
        availableGroup.setId(1L);

        when(groupParticipantService.checkParticipation(userId)).thenReturn(false);
        when(userService.checkCoinLevelAndRewardClaim(userId)).thenReturn(1);
        when(tournamentGroupService.findAnAvailableGroup(userId, currentTournament)).thenReturn(availableGroup);
        when(userService.getUser(userId)).thenReturn(new User());
        when(groupParticipantService.saveGroupParticipant(any(GroupParticipant.class))).thenReturn(new GroupParticipant());
        when(tournamentGroupService.getGroupLeaderboardOnJoin(eq(availableGroup), any(GroupParticipant.class)))
                .thenReturn(List.of(new HashMap<>()));

        ResponseEntity<HashMap<String, Object>> response = tournamentOrganizer.acceptEntryRequest(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(Objects.requireNonNull(response.getBody()).get("Group Leaderboard"));
    }

    @Test
    public void acceptEntryRequest_UserAlreadyParticipated() {
        Long userId = 1L;

        when(groupParticipantService.checkParticipation(userId)).thenReturn(true);

        ResponseEntity<HashMap<String, Object>> response = tournamentOrganizer.acceptEntryRequest(userId);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals("Katılım önceden sağlanmıştır.", Objects.requireNonNull(response.getBody()).get("message"));
    }
}
