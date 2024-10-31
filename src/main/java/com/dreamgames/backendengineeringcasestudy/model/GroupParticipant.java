package com.dreamgames.backendengineeringcasestudy.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="group_participants")
@Data
public class GroupParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @ManyToOne
    @JoinColumn(name = "group_id" , nullable = false)
    private TournamentGroup tournamentGroup;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    private int score ;

}
