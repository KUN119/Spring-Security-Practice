package com.example.testsecurity.dto;

import lombok.Getter;
import lombok.Setter;

//join.mustache에서 보내는 username과 password 두 가지만 받으면 됨
@Getter
@Setter
public class JoinDTO {
    private String username;
    private String password;
}
