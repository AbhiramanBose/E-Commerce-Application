package com.pm.userauthenticationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Session extends BaseModel{
    private String token;

    @ManyToOne
    private User user;

}
//1             m  (m-1) has been inactive
// User         Session
// 1            1

//1             m
