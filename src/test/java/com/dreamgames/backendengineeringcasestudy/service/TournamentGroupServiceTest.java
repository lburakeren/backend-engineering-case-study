package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.model.GroupParticipant;
import com.dreamgames.backendengineeringcasestudy.model.Tournament;
import com.dreamgames.backendengineeringcasestudy.model.TournamentGroup;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.TournamentGroupRepository;

import org.junit.Before;
import org.junit.Test;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



public class TournamentGroupServiceTest {

    @Mock
    private TournamentGroupRepository tournamentGroupRepository;

    @Mock
    private UserService userService ;

    @InjectMocks
    private TournamentGroupService tournamentGroupService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAnAvailableGroup() {

        Long userId  = 1L ;
        User user = new User();
        user.setId(userId);
        user.setCountry("TURKEY");

        List<GroupParticipant> participants = createRandomParticipants1();
        TournamentGroup tournamentGroup = new TournamentGroup();
        tournamentGroup.setGroupParticipants(participants);
        tournamentGroup.setReady(true);
        List<TournamentGroup> groups = new ArrayList<>();
        groups.add(tournamentGroup);

        when(userService.getUser(userId)).thenReturn(user);
        when(tournamentGroupRepository.findAvailableGroups()).thenReturn(groups);

        TournamentGroup expectedGroup = tournamentGroupService.findAnAvailableGroup(userId,new Tournament());

        assertNotNull(expectedGroup);
        assertEquals(4,expectedGroup.getGroupParticipants().size());
        assertTrue(expectedGroup.isReady());
        verify(tournamentGroupRepository).findAvailableGroups();
        verify(userService).getUser(userId);

    }


    @Test
    public void getGroupLeaderboard() {

        Long groupId = 1L ;
        TournamentGroup existingGroup = new TournamentGroup();
        existingGroup.setId(groupId);
        List<GroupParticipant> participants = createRandomParticipants2();
        existingGroup.setGroupParticipants(participants);

        List<HashMap<String, Object>> expectedLeaderboard = new ArrayList<>();

        for (GroupParticipant participant : participants) {
            HashMap<String, Object> entry = new HashMap<>();
            entry.put("userId", participant.getUser().getId());
            entry.put("username", participant.getUser().getUserName());
            entry.put("country", participant.getUser().getCountry());
            entry.put("score", participant.getScore());
            expectedLeaderboard.add(entry);
        }

        when(tournamentGroupRepository.findById(groupId)).thenReturn(Optional.of(existingGroup));

        List<HashMap<String, Object>> returnedLeaderboard = tournamentGroupService.getGroupLeaderboard(groupId);

        assertNotNull(returnedLeaderboard);
        assertEquals(5,returnedLeaderboard.size());
        assertEquals(100,returnedLeaderboard.get(0).get("score"));
        verify(tournamentGroupRepository).findById(groupId);

    }

    @Test
    public void getGroups() {
        Tournament tournament = new Tournament();
        tournament.setId(3L);


        TournamentGroup group = new TournamentGroup();
        group.setTournament(tournament);
        group.setId(2L);
        group.setGroupParticipants(createRandomParticipants2());
        group.setReady(true);

        TournamentGroup group2 = new TournamentGroup();
        group2.setTournament(tournament);
        group2.setId(4L);
        group2.setGroupParticipants(createRandomParticipants2());
        group2.setReady(true);

        List<TournamentGroup> expectedGroups = new ArrayList<>();
        expectedGroups.add(group);
        expectedGroups.add(group2);

        when(tournamentGroupRepository.findReadyGroupsByTournamentId(tournament.getId())).thenReturn(expectedGroups);

        List<TournamentGroup> returnedGroups = tournamentGroupService.getGroups(tournament.getId());

        assertNotNull(returnedGroups);
        assertEquals(2, returnedGroups.size());
        assertTrue(returnedGroups.contains(group));
        assertTrue(returnedGroups.contains(group2));


    }



    private List<GroupParticipant> createRandomParticipants1() {
        List<GroupParticipant> participants = new ArrayList<>();

        User user1 = new User();
        user1.setCountry("USA");
        GroupParticipant gp1 = new GroupParticipant();
        gp1.setUser(user1);
        participants.add(gp1);

        User user2 = new User();
        user2.setCountry("FRANCE");
        GroupParticipant gp2 = new GroupParticipant();
        gp2.setUser(user2);
        participants.add(gp2);

        User user3 = new User();
        user3.setCountry("GERMANY");
        GroupParticipant gp3 = new GroupParticipant();
        gp3.setUser(user3);
        participants.add(gp3);

        User user4 = new User();
        user4.setCountry("UK");
        GroupParticipant gp4 = new GroupParticipant();
        gp4.setUser(user4);
        participants.add(gp4);

        return participants;
    }


    private List<GroupParticipant> createRandomParticipants2() {
        List<GroupParticipant> participants = new ArrayList<>();

        User user1 = new User();
        user1.setId(1L);
        user1.setUserName("John Doe");
        user1.setCountry("USA");
        GroupParticipant gp1 = new GroupParticipant();
        gp1.setUser(user1);
        gp1.setScore(100);
        participants.add(gp1);

        User user2 = new User();
        user2.setId(2L);
        user2.setUserName("Kemal Karakas");
        user2.setCountry("FRANCE");
        GroupParticipant gp2 = new GroupParticipant();
        gp2.setUser(user2);
        gp2.setScore(80);
        participants.add(gp2);

        User user3 = new User();
        user3.setId(3L);
        user3.setUserName("Burak EREN");
        user3.setCountry("GERMANY");
        GroupParticipant gp3 = new GroupParticipant();
        gp3.setUser(user3);
        gp3.setScore(70);
        participants.add(gp3);

        User user4 = new User();
        user4.setId(4L);
        user4.setUserName("Kerem Akturkoglu");
        user4.setCountry("UK");
        GroupParticipant gp4 = new GroupParticipant();
        gp4.setUser(user4);
        gp4.setScore(50);
        participants.add(gp4);


        User user5 = new User();
        user5.setId(6L);
        user5.setUserName("Arda Guler");
        user5.setCountry("TURKEY");
        GroupParticipant gp5 = new GroupParticipant();
        gp5.setUser(user5);
        gp5.setScore(40);
        participants.add(gp5);

        return participants;


    }




}