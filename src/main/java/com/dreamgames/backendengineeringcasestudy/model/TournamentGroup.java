package com.dreamgames.backendengineeringcasestudy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tournament_groups")
@Data
public class TournamentGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isReady = false ;

    @ManyToOne
    @JoinColumn(name = "tournament_id" , nullable = false)
    @OnDelete( action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Tournament tournament;

    @OneToMany(mappedBy = "tournamentGroup", cascade = CascadeType.ALL)
    private List<GroupParticipant> groupParticipants = new ArrayList<>();

}
