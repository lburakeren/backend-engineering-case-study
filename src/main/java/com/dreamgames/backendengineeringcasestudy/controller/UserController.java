package com.dreamgames.backendengineeringcasestudy.controller;

import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.service.GroupParticipantService;
import com.dreamgames.backendengineeringcasestudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/createUser")
    public User createUser(@RequestBody User newUser){
        return userService.createNewUser(newUser);
    }

    @PutMapping("/updateLevel/{userId}")
    public ResponseEntity<Object> updateLevel(@PathVariable Long userId) {
        try {
            User updatedUser = userService.updateLevelByUserId(userId);

            boolean isIncremented = userService.incrementParticipantScore(userId);

            if(isIncremented){
                userService.incrementCountryScore(userId);
            }

            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PutMapping("/ClaimReward/{userId}")
    public ResponseEntity<Object> ClaimReward(@PathVariable Long userId) {
        try {
            User updatedUser = userService.ClaimReward(userId);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
