package com.jyanoos.qna.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User  {
    int id;
    String email;
    String nickname;
}