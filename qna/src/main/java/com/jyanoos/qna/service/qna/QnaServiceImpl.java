package com.jyanoos.qna.service.qna;

import com.jyanoos.qna.domain.Lecture;
import com.jyanoos.qna.domain.Qna;
import com.jyanoos.qna.domain.QnaConst;
import com.jyanoos.qna.domain.Student;
import com.jyanoos.qna.mapper.QnaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QnaServiceImpl implements QnaService {
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
        log.info("success is {}",success);
        Lecture newLecture = qnaMapper.selectLectureByName(lectureName,professorId);//리턴용데이터

        return newLecture;
    }

    @Override//강의명 변경
    public Lecture modifyLecture(Lecture lecture, String newLectureName, String professorName) {
        return null;
    }

    @Override//강의에 해당하는 학생 리스트 리턴
    public List<List<Student>> getStudents(String lectureName, String professorName) {
        List<Student> studentList = qnaMapper.selectStudentsByLectureName(lectureName,professorName);
        if(studentList.size()==0){
            log.info("{} 학생없음",lectureName);
            return null;
        }
//        for(Student student:studentList){
//            student.setLastQnaDate(qnaMapper.selectQnaByStdNameLcName(student.getName(),lectureName));
//        }학생 qna리스트가 lastQnaDate로 바꼈다면 성공
        int col_num = QnaConst.STUDENT_COLUMN_NUM;
        log.info("현재 설정된 줄 수: {}",col_num);

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
    public Student pickQnaStudent(String lectureName, String mode, String professorName) {
        log.info("qna대상 학생 검색 시작 - mode = {} lecture ={}",mode, lectureName);
        List<Student> studentList;//랜덤추출 대상 학생 리스트

        //최적추출
        if(mode.equals("fit")){
            int minQnaTimes = qnaMapper.getMinQnaTimes(lectureName,professorName);
            log.info("최적적합 검색, minQnaTimes = {}", minQnaTimes);

            Date minQnaDate = qnaMapper.lastQnaDate(lectureName, minQnaTimes,professorName);
            log.info("minQnaDate = {}",minQnaDate);

            if(minQnaDate==null){
                studentList = qnaMapper.pickGroupLastQnaIsNull(lectureName,professorName);
            }else{
                studentList = qnaMapper.selectStudentMinQna(lectureName,minQnaTimes,minQnaDate,professorName);
            }

            //랜덤추출
        }else studentList = qnaMapper.selectStudentsByLectureName(lectureName,professorName);

        Collections.shuffle(studentList);
        return studentList.get(0);
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

    @Override
    public List<Student> addStdList(String[] stdList, String lectureName, String professorName) {
        log.info("학생 리스트 등록 서비스 시작");
        List<Student> studentList = new ArrayList<>();


        for(int i=0;i<stdList.length;i++){
            //String studentName = stdList[i].replace("\n","").replace(" ","").replace("\\a","");
            String studentName = stdList[i];
            log.info("stdList[i] is {}",stdList[i]);
            log.info("학생 {}을 {}에 등록합니다",studentName,lectureName);
            qnaMapper.insertStudent(studentName,lectureName,professorName);
            Student addedStudent = qnaMapper.selectStudentByNameLcName(studentName, lectureName,professorName);
            studentList.add(addedStudent);
        }
        return studentList;
    }

    @Override
    public Qna addQna(String studentName, String lectureName,String professorName) {
        log.info("새 질문 생성! 학생명-{}, 강의명-{}",studentName,lectureName);
        int i = qnaMapper.insertQna(studentName, lectureName, professorName);
        Qna qna = qnaMapper.selectRecentQna(studentName,lectureName, professorName);
        qnaMapper.updateStudentLastQnaDate(studentName,lectureName,qna.getQnaTime(),professorName);
        return qna;
    }

    @Override
    public int removeStudent(String nowLecture, String stdName, String professorName) {
        int i = qnaMapper.deleteStudentByNameLcName(stdName, nowLecture,professorName);
        return i;
    }

    @Override
    public boolean removeLecture(String removeLectureName, String professorName) {
        int i = qnaMapper.deleteLectureByName(removeLectureName,professorName);
        if(i==1) return true;
        else return false;
    }
}
