package com.jyanoos.qna.domain;

public enum MemberType {
    PROFESSOR(0),
    STUDENT(1);

    private final int number;

    MemberType(int number){
        this.number=number;
    }
}
