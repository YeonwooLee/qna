package com.jyanoos.qna.service;

import com.jyanoos.qna.domain.Lecture;
import com.jyanoos.qna.domain.Student;
import com.jyanoos.qna.mapper.QnaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QnaServiceImpl implements QnaService{
    private final QnaMapper qnaMapper;

    @Override//교수에 해당하는 강의 리스트 리턴
    public List<Lecture> getLectureList(String professorName) {
        List<Lecture> lectureList = qnaMapper.selectLecturesByProfessorName(professorName);
        return lectureList;
    }

    @Override//교수에 강의 추가
    public Lecture addLecture(String professorId,String lectureName) {
        int count = qnaMapper.counttLectureByName(lectureName);//중복확인
        int success = qnaMapper.insertLecture(lectureName, professorId);//성공확인
        Lecture newLecture = qnaMapper.selectLectureByName(lectureName);//리턴용데이터

        return newLecture;
    }

    @Override//강의명 변경
    public Lecture modifyLecture(Lecture lecture, String newLectureName) {
        return null;
    }

    @Override//강의에 해당하는 학생 리스트 리턴
    public List<List<Student>> getStudents(String lectureName) {
        List<Student> studentList = qnaMapper.selectStudentsByLectureName(lectureName);

        int col_num = 3;
        log.info("줄 맞춤 되어있는 학생 리스트 생성 - 현재 설정된 줄 수: {}",col_num);

        List<List<Student>> studentListArr = new ArrayList<>(); //최종
        int studentIndex = 0;
        while(true){
            List<Student> tempStudentList = new ArrayList<>();//임시
            for(int j=0;j<col_num;j++){
                if(studentIndex>=studentList.size()){
                    tempStudentList.add(new Student());
                }else {
                    tempStudentList.add(studentList.get(studentIndex));
                }
                studentIndex+=1;
            }
            studentListArr.add(tempStudentList);
            if(studentIndex>=studentList.size()){
                break;
            }
        }
        return studentListArr;
    }

    @Override
    public Student getStudentByIdx(int idx) {
        Student student = qnaMapper.selectStudentByIdx(idx);
        return student;
    }

    @Override//학생 qnaTimes 수정
    public Student modifyQnaTimes(int studentIdx, int afterTimes) {
        int i = qnaMapper.updateStudentQnaTimesByIdx(studentIdx, afterTimes);
        log.info("질문 타임 수정, 수정된 행 {}",i);
        Student student = qnaMapper.selectStudentByIdx(studentIdx);
        return student;
    }
}
