package com.dreamgames.backendengineeringcasestudy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name="tournaments")
@Data
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date tournamentDate;

    private String tournamentName = "World Cup" ;

    private boolean isActive = true ;

    private int uk_score = 0 ;
    private int usa_score = 0 ;
    private int fra_score = 0 ;
    private int tur_score = 0 ;
    private int ger_score = 0 ;


}
