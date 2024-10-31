package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.model.GroupParticipant;
import com.dreamgames.backendengineeringcasestudy.model.TournamentGroup;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private GroupParticipantService groupParticipantService;

    @Mock
    private TournamentService tournamentService;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNewUser() {
        User newUser = new User();
        newUser.setUserName("John Doe");

        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User createdUser = userService.createNewUser(newUser);

        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getUserName());
        verify(userRepository).save(createdUser);
    }

    @Test
    public void testUpdateLevelByUserId() throws Exception {

        Long userId = 31L;
        User foundUser  = new User();
        foundUser.setId(userId);
        foundUser.setLevel(31);
        foundUser.setCoins(30450);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(foundUser));
        when(userRepository.save(any(User.class))).thenReturn(foundUser);

        User updatedUser = userService.updateLevelByUserId(userId);

        assertNotNull(updatedUser);
        assertEquals(32,updatedUser.getLevel());
        assertEquals(30475,foundUser.getCoins());
        verify(userRepository).save(updatedUser);

    }

    @Test
    public void testIncrementParticipantScore_Participated() {

        Long userId = 1L;

        when(groupParticipantService.incrementScore(userId)).thenReturn(true);

        boolean result = userService.incrementParticipantScore(userId);

        assertTrue(result);
        verify(groupParticipantService).incrementScore(userId);
    }

    @Test
    public void testIncrementParticipantScore_NotParticipated() {

        Long userId = 1L;

        when(groupParticipantService.incrementScore(userId)).thenReturn(false);

        boolean result = userService.incrementParticipantScore(userId);

        assertFalse(result);
        verify(groupParticipantService).incrementScore(userId);
    }

    @Test
    public void testGetUser_UserExists(){
        Long userId = 2L;
        User user = new User();
        user.setId(userId);
        user.setUserName("Test User");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = userService.getUser(userId);

        assertNotNull(foundUser);
        assertEquals(user.getUserName(),foundUser.getUserName());
        verify(userRepository).findById(userId);


    }

    @Test
    public void testGetUser_UserDNE(){

        Long userId = 2L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        User foundUser = userService.getUser(userId);

        assertNull(foundUser);
        verify(userRepository).findById(userId);

    }

}