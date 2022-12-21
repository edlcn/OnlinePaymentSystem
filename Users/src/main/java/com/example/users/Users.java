package com.example.users;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String mail;

    private String gender;

    private String password;

    private boolean isAdmin;

    private double balance;

    private boolean isActive;

    public Users(UsersDTReq user){
        this.balance = 0.0;
        this.isAdmin = false;
        this.mail = user.getMail();
        this.gender = user.getGender();
        this.password = user.getPassword();
        this.name = user.getName();
        this.isActive = false;
    }

}
