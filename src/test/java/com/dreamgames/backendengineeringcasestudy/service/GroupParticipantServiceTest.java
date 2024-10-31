package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.model.GroupParticipant;
import com.dreamgames.backendengineeringcasestudy.model.TournamentGroup;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.GroupParticipantRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GroupParticipantServiceTest {

    @Mock
    private GroupParticipantRepository groupParticipantRepository;

    @InjectMocks
    private GroupParticipantService groupParticipantService;

    @Before
    public void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveGroupParticipant() {

        GroupParticipant expectedGroupParticipant = new GroupParticipant();
        expectedGroupParticipant.setId(1L);

        when(groupParticipantRepository.save(expectedGroupParticipant)).thenReturn(expectedGroupParticipant);

        GroupParticipant returnedGroupParticipant = groupParticipantService.saveGroupParticipant(expectedGroupParticipant);

        assertNotNull(returnedGroupParticipant);
        assertEquals(returnedGroupParticipant.getId(),expectedGroupParticipant.getId());

        verify(groupParticipantRepository).save(expectedGroupParticipant);

    }

    @Test
    public void testIncrementScore() {

        User user = new User();
        user.setId(1L);

        TournamentGroup group = new TournamentGroup();
        group.setReady(true);

        GroupParticipant groupParticipant = new GroupParticipant();
        groupParticipant.setTournamentGroup(group);
        groupParticipant.setUser(user);
        int score = 1 ;
        groupParticipant.setScore(score);

        when(groupParticipantRepository.findByUserId(user.getId())).thenReturn(Optional.of(groupParticipant));

        boolean check = groupParticipantService.incrementScore(user.getId());

        assertTrue(check);
        assertEquals(score+1,groupParticipant.getScore());

        verify(groupParticipantRepository).findByUserId(user.getId());
        verify(groupParticipantRepository).save(groupParticipant);



    }

    @Test
    public void testGetGroupRank() {
        User user = new User();
        user.setId(1L);

        TournamentGroup group = new TournamentGroup();
        group.setReady(true);

        GroupParticipant gp1 = new GroupParticipant();
        gp1.setTournamentGroup(group);
        gp1.setUser(user);
        gp1.setScore(100);

        User user2 = new User();
        user2.setId(2L);
        GroupParticipant gp2 = new GroupParticipant();
        gp2.setTournamentGroup(group);
        gp2.setUser(user2);
        gp2.setScore(9);

        User user3 = new User();
        user3.setId(3L);
        GroupParticipant gp3 = new GroupParticipant();
        gp3.setTournamentGroup(group);
        gp3.setUser(user3);
        gp3.setScore(8);

        User user4 = new User();
        user4.setId(4L);
        GroupParticipant gp4 = new GroupParticipant();
        gp4.setTournamentGroup(group);
        gp4.setUser(user4);
        gp4.setScore(7);

        User user5 = new User();
        user5.setId(5L);
        GroupParticipant gp5 = new GroupParticipant();
        gp5.setTournamentGroup(group);
        gp5.setUser(user5);
        gp5.setScore(6);


        List<GroupParticipant> participants = new ArrayList<>();
        participants.add(gp1);
        participants.add(gp2);
        participants.add(gp3);
        participants.add(gp4);
        participants.add(gp5);

        group.setGroupParticipants(participants);

        when(groupParticipantRepository.findByUserId(user.getId())).thenReturn(Optional.of(gp1));

        int rank = groupParticipantService.getGroupRank(user.getId());

        assertEquals(1, rank);
        verify(groupParticipantRepository).findByUserId(user.getId());
    }


    @Test
    public void testGetGroupParticipant() {
        User user = new User();
        user.setId(1L);

        GroupParticipant expectedGP = new GroupParticipant();
        expectedGP.setUser(user);

        when(groupParticipantRepository.findByUserId(user.getId())).thenReturn(Optional.of(expectedGP));

        Optional<GroupParticipant> returnedGP = groupParticipantService.getGroupParticipant(user.getId());

        assertNotNull(returnedGP);
        assertEquals(user.getId(),returnedGP.get().getUser().getId());
        verify(groupParticipantRepository).findByUserId(user.getId());



    }

    @Test
    public void testCheckParticipation() {

        Long userId = 31L ;

        when(groupParticipantRepository.existsByUserId(userId)).thenReturn(true);

        boolean check = groupParticipantService.checkParticipation(userId);

        assertTrue(check);
        verify(groupParticipantRepository).existsByUserId(userId);

    }



}