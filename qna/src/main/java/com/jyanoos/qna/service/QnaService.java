package com.jyanoos.qna.service;

import com.jyanoos.qna.domain.Lecture;
import com.jyanoos.qna.domain.Student;

import java.util.List;

public interface QnaService {
    //교수에 해당하는 강의 리스트 리턴
    List<Lecture> getLectureList(String professorName);

    //교수에 강의 추가
    Lecture addLecture(String professorId,String lectureName);



    //강의명 변경
    Lecture modifyLecture(Lecture lecture, String newLectureName);





    //강의에 해당하는 학생 리스트 리턴
    List<List<Student>> getStudents(String lectureName);

    //단일 학생 검색(idx)
    Student getStudentByIdx(int idx);


    //학생 qnaTimes 수정
    Student modifyQnaTimes(int studentIdx, int times);

}
