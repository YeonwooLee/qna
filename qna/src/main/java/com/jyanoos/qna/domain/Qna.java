package com.jyanoos.qna.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
@Getter
@Setter
@ToString
public class Qna {
    int idx;
    Timestamp qnaTime;
    String studentName;
    String lectureName;

}
