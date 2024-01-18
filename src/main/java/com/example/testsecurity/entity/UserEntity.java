package com.example.testsecurity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ID 자동 생성을 위한 어노테이션
    private int id;

    @Column(unique = true) //username의 중복을 막기 위해 컬럼에 유니크 설정
    private String username;

    private String password;

    private String role; //권한 user,admin..
}
