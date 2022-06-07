package com.jyanoos.qna.mapper;

import com.jyanoos.qna.domain.Lecture;
import com.jyanoos.qna.domain.Professor;
import com.jyanoos.qna.domain.Qna;
import com.jyanoos.qna.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest //스프링 부트 테스트
@Slf4j
public class QnaMapperTest {
    @Autowired
    QnaMapper qnaMapper;
    @Test
    void 삽입통합테스트(){
        교수삽입검색();
        강의삽입검색();
        학생삽입검색();
        qna삽입검색();
    }
    @Test
    void 테스트데이터전체삭제(){
        qna삭제();
        학생삭제();
        강의삭제();
        교수삭제();
    }
    @Test
    void 교수삽입검색(){
        qnaMapper.insertProfessor("pfId001","pfName001","pwdpwd");
        Professor professor = qnaMapper.selectProfessorById("pfId001");
        log.info("교수: {}",professor);
    }
    @Test
    void 강의삽입검색(){
        qnaMapper.insertLecture("lcName001","pfId001");
        List<Lecture> lectureList = qnaMapper.selectLecturesByProfessorName("pfId001");
        log.info("강의: {}",lectureList);
    }

    @Test
    void 학생삽입검색(){
        qnaMapper.insertStudent("stdId001","stdName001","lcName001");
        List<Student> students = qnaMapper.selectStudentsByLectureName("lcName001");
        log.info("학생: {}",students);
    }
    @Test
    void qna삽입검색(){
        qnaMapper.insertQna("stdId001","lcName001");
        List<Qna> qna = qnaMapper.selectQnaByStdIdLecId("stdId001","lcName001");
        log.info("qna : {}",qna);
    }


    @Test
    void qna삭제(){
        qnaMapper.delteQna("stdId001","lcName001");
    }
    @Test
    void 학생삭제(){
        qnaMapper.deleteStudentByName("stdName001");
    }
    @Test
    void 강의삭제(){
        qnaMapper.deleteLectureById("lcName001");
    }
    @Test
    void 교수삭제(){
        qnaMapper.deleteProfessor("pfId001");
    }



    @Test
    void 강의수(){
        int i = qnaMapper.counttLectureByName("김강의");
        log.info("강의 수 {}",i);
    }


}
