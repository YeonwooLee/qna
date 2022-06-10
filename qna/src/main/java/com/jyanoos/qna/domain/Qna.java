package com.jyanoos.qna.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;
@Getter
@Setter
@ToString
public class Qna {
    int idx;
    Date qnaTime;
    String studentName;
    String lectureName;

}
