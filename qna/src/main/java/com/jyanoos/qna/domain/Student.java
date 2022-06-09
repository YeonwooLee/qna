package com.jyanoos.qna.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Student {
    int idx;
    String name;
    String lecture_id;
    int qnaTimes;
}
