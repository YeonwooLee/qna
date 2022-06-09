package com.jyanoos.qna.service;

import com.jyanoos.qna.domain.Professor;
import com.jyanoos.qna.domain.Student;
import com.jyanoos.qna.mapper.QnaMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
//Qna 서비스 테스트
public class QnaServiceTest {
    @Autowired
    QnaService qnaService;

    @Test
    void 강의추가(){
        qnaService.addLecture("p1","김강의");
    }
    @Test
    void 학생리스트(){
        qnaService.modifyQnaTimes(1,3);

    }
}
