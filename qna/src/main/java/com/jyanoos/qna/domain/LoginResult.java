package com.jyanoos.qna.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginResult {
    boolean success;
    Professor professor;
    String message;
}
