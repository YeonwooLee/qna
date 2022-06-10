package com.jyanoos.qna.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
public class Student {
    int idx;
    String name;
    String lectureName;
    int qnaTimes;
    Timestamp lastQnaDate;
}
