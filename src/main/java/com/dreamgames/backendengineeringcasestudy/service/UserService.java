package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.model.GroupParticipant;
import com.dreamgames.backendengineeringcasestudy.model.TournamentGroup;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private static final List<String> countries = Arrays.asList("TURKEY", "USA", "GERMANY", "UK", "FRANCE");

    private static final Random random = new Random();

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private GroupParticipantService groupParticipantService;

    @Autowired
    private TournamentService tournamentService ;


    public User createNewUser(User newUser) {
        newUser.setCountry(countries.get(random.nextInt(countries.size())));
        return userRepository.save(newUser);
    }


    public User updateLevelByUserId(Long userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            User foundUser = user.get();
            foundUser.setLevel(foundUser.getLevel()+1);
            foundUser.setCoins(foundUser.getCoins()+25);
            userRepository.save(foundUser);
            return foundUser;
        }else {
            throw new Exception("Kullanıcı bulunamadı.") ;
        }
    }

    public User ClaimReward(Long userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            User foundUser = user.get();
            if(foundUser.getRewardCoin() != 0 ){
                foundUser.setCoins(foundUser.getRewardCoin()+ foundUser.getCoins());
                foundUser.setRewardCoin(0);
                userRepository.save(foundUser);
                return foundUser;
            }else{
                throw new Exception("Ödül yok.") ;
            }
        }else{
            throw new Exception("Kullanıcı bulunamadı.") ;
        }
    }



    public int checkCoinLevelAndRewardClaim(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            User foundUser = user.get();
            if(foundUser.getLevel()>=20 && foundUser.getCoins()>1000){
                return 1 ;
            }else if ( foundUser.getRewardCoin() > 0 ){
                return -2;
            } else {
                return 0 ;
            }
        }else {
            return -1 ;
        }
    }


    public User getUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    public boolean incrementParticipantScore(Long userId)  {
        return groupParticipantService.incrementScore(userId);
    }

    public void incrementCountryScore(Long userId) {
        Optional<GroupParticipant> gp = groupParticipantService.getGroupParticipant(userId);

        if (gp.isPresent()) {
            GroupParticipant foundGP = gp.get();
            TournamentGroup tournamentGroup = foundGP.getTournamentGroup();

            Long tournamentId = tournamentGroup.getTournament().getId();

            tournamentService.updateCountryScore(tournamentId,getUser(userId).getCountry());

        }

    }

    public void saveFirstPlaceReward(User user) {
        user.setRewardCoin(10000);
        userRepository.save(user);
    }

    public void saveSecondPlaceReward(User user) {
        user.setRewardCoin(5000);
        userRepository.save(user);
    }



}
