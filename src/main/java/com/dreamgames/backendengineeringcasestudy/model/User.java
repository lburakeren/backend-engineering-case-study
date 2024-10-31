package com.dreamgames.backendengineeringcasestudy.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String userName ;

    private int level = 1 ;
    private int coins = 5000 ;

    private String country ;

    private int rewardCoin = 0 ;

}
