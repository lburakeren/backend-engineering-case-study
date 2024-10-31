package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.model.Tournament;
import com.dreamgames.backendengineeringcasestudy.repository.TournamentRepository;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TournamentService {


    @Autowired
    private TournamentRepository tournamentRepository;

    public void save(Tournament tournament) {
        tournamentRepository.save(tournament);
    }


    @Async
    public void updateCountryScore(Long tournamentId, String country) {
        Optional<Tournament> tournament = tournamentRepository.findById(tournamentId);
        if (tournament.isPresent()) {
            Tournament foundTournament = tournament.get();
            switch (country) {
                case "TURKEY":
                    foundTournament.setTur_score(foundTournament.getTur_score() + 1);
                    tournamentRepository.save(foundTournament);
                    break;
                case "USA":
                    foundTournament.setUsa_score(foundTournament.getUsa_score() + 1);
                    tournamentRepository.save(foundTournament);
                    break;
                case "GERMANY":
                    foundTournament.setGer_score(foundTournament.getGer_score() + 1);
                    tournamentRepository.save(foundTournament);
                    break;
                case "UK":
                    foundTournament.setUk_score(foundTournament.getUk_score() + 1);
                    tournamentRepository.save(foundTournament);
                    break;
                case "FRANCE":
                    foundTournament.setFra_score(foundTournament.getFra_score() + 1);
                    tournamentRepository.save(foundTournament);
                    break;
                default:
                    break;
            }
        }
    }

    @Synchronized
    public LinkedHashMap<String, Object> getCountryLeaderboard(Long tournamentId) {
        Optional<Tournament> tournament = tournamentRepository.findById(tournamentId);
        LinkedHashMap<String, Object> leaderboard = new LinkedHashMap<>();

        if (tournament.isPresent()) {
            Tournament foundTournament = tournament.get();

            leaderboard.put("UK SCORE", foundTournament.getUk_score());
            leaderboard.put("USA SCORE", foundTournament.getUsa_score());
            leaderboard.put("FRANCE SCORE", foundTournament.getFra_score());
            leaderboard.put("TURKEY SCORE", foundTournament.getTur_score());
            leaderboard.put("GERMANY SCORE", foundTournament.getGer_score());

            List<Map.Entry<String, Object>> sortedLeaderboard = leaderboard.entrySet()
                    .stream()
                    .sorted((entry1, entry2) -> Integer.compare((Integer) entry2.getValue(), (Integer) entry1.getValue()))
                    .toList();

            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            for (Map.Entry<String, Object> entry : sortedLeaderboard) {
                map.put(entry.getKey(), entry.getValue());
            }

            return map;
        }

        return leaderboard;
    }

    @Async
    public Tournament getCurrentActiveTournament() {
        return tournamentRepository.findByIsActiveTrue().orElse(null);
    }

}
