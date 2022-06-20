package com.jyanoos.qna.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Professor {
    int idx;
    String name;
    String password;
    String pageTitle;
    String pageSubTitle;
    String pageFooterMsg;
}
