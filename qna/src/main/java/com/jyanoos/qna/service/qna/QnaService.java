package com.jyanoos.qna.service.qna;

import com.jyanoos.qna.domain.Lecture;
import com.jyanoos.qna.domain.Qna;
import com.jyanoos.qna.domain.Student;

import java.util.List;

public interface QnaService {
    //교수에 해당하는 강의 리스트 리턴
    List<Lecture> getLectureList(String professorName);

    //교수에 강의 추가
    Lecture addLecture(String professorId,String lectureName);



    //강의명 변경
    Lecture modifyLecture(Lecture lecture, String newLectureName, String professorName);




    //강의에 해당하는 학생 리스트 리턴(줄바꿈 처리 off)
    List<Student> getStudentsNoLine(String lectureName,String professorName);

    //강의에 해당하는 학생 리스트 리턴(줄바꿈 처리 on)
    List<List<Student>> getStudents(String lectureName, String professorName);

    //학생추첨
    Student pickQnaStudent(String lecturelectureName, String mode, String professorName);


    //단일 학생 검색(idx)
    Student getStudentByIdx(int idx);


    //학생 qnaTimes 수정
    Student modifyQnaTimes(int studentIdx, int times);

    //학생을 리스트로 한번에 추가
    List<Student> addStdList(List<String> studentArr,String lectureName, String professorName);



    //qna 삽입
    Qna addQna(String studentName, String lectureName, String professorName);

    //학생 삭제
    int removeStudent(String nowLecture, String stdName, String professorName);

    boolean removeLecture(String removeLectureName, String professorName);


}
