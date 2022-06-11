package com.jyanoos.qna.mapper;

import com.jyanoos.qna.domain.Lecture;
import com.jyanoos.qna.domain.Professor;
import com.jyanoos.qna.domain.Qna;
import com.jyanoos.qna.domain.Student;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface QnaMapper {
    //교수CRUD

    //교수 삽입
    @Insert("INSERT INTO professor(id, name, password)" +
            "VALUES(#{id},#{name},#{password})")
    int insertProfessor(
            @Param("id") String id,
            @Param("name") String name,
            @Param("password") String password
    ) ;

    //교수 검색
    @Select("SELECT * FROM professor where idx = #{idx}")
    Professor selectProfessorByIdx(
            @Param("idx") int idx
    );
    @Select("SELECT * FROM professor where name = #{name}")
    Professor selectProfessorByname(
            @Param("name") String name
    );
    @Select("SELECT count(*) FROM professor where name = #{name}")
    int countProfessorByname(
            @Param("name") String name
    );

    //교수 삭제
    @Delete("DELETE FROM professor where id = #{id}")
    int deleteProfessor(@Param("id")String id);




    //강의 CRUD
    //강의 삽입
    @Insert("INSERT INTO lecture(name, professorName)" +
            "VALUES(#{name}, #{professorName})")
    int insertLecture(
            @Param("name") String name,
            @Param("professorName") String professorName
    );

    //강의 검색(교수에 해당하는)
    @Select("SELECT * FROM lecture where professorName=#{professorName} order by idx asc")
    List<Lecture> selectLecturesByProfessorName(
            @Param("professorName") String professorName
    );

    //강의명 수 하나인지 계수
    @Select("select count(*) from lecture where name = #{name}")
    int counttLectureByName(@Param("name")String name);
    //강의 검색(강의명)
    @Select("select * from lecture where name = #{name}")
    Lecture selectLectureByName(@Param("name")String name);



    //강의명 변경
    @Update("UPDATE lecture SET name=#{name_after} where name=#{name_before}")
    int updateLectureByName(@Param("name_before")String name_before, @Param("name_after")String name_after);

    //강의 삭제
    @Delete("DELETE FROM lecture WHERE name = #{name}")
    int deleteLectureByName(
            @Param("name") String name
    );




    //학생 CRUD
    //학생 삽입
    @Insert("INSERT INTO student(name, lectureName)" +
            "VALUES(#{name}, #{lectureName})")
    int insertStudent(
        @Param("name") String name,
        @Param("lectureName") String lectureName
    );

    //학생 그룹 검색 lecture_name
    @Select("SELECT * FROM student where lectureName=#{lectureName} order by qnaTimes asc")
    List<Student> selectStudentsByLectureName(@Param("lectureName") String lectureName);

    //학생 최소 qnaTimes
    @Select("SELECT MIN(qnaTimes) FROM student WHERE lectureName =#{lectureName}")
    int getMinQnaTimes(@Param("lectureName")String lectureName);

    //qnaTimes가 n인 학생수 중 가장 오래전에 질문한 날짜
    @Select("SELECT MIN(lastQnaDate) FROM student WHERE lectureName =#{lectureName} AND qnaTimes=#{qnaTimes}")
    Date lastQnaDate(@Param("lectureName")String lectureName,@Param("qnaTimes")int qnaTimes);

    //가장질문 적은학생 중 가장 오래전에 질문한 학생(qnaTimes lastQnaDate)
    @Select("SELECT * FROM student where lectureName=#{lectureName} and qnaTimes=#{qnaTimes} and lastQnaDate=#{lastQnaDate}")
    List<Student> selectStudentMinQna(@Param("lectureName")String lectureName, @Param("qnaTimes") int qnaTimes, @Param("lastQnaDate")Date lastQnaDate);

    //학생 그룹 검색 최적적합(min(qnaTimes)!=0)
    @Select("SELECT * FROM student WHERE \n" +
            "\tqnaTimes = \t(SELECT MIN(qnaTimes) FROM student WHERE lectureName =#{lectureName})\n" +
            "\tAND \n" +
            "\tlastQnaDate = (SELECT MIN(lastQnaDate) FROM student WHERE lectureName =#{lectureName})\n" +
            "\tAND\n" +
            "\tlectureName = #{lectureName}")
    List<Student> pickGroup(@Param("lectureName")String lectureName);

    //학생 그룹 검색 최적적합(min(qnaTimes)=0)
    @Select("SELECT * FROM student WHERE \n" +
            "\tqnaTimes = \t(SELECT MIN(qnaTimes) FROM student WHERE lectureName =#{lectureName})\n" +
            "\tAND \n" +
            "\tnvl(lastQnaDate,-1) = -1\n" +
            "\tAND\n" +
            "\tlectureName = #{lectureName};")
    List<Student> pickGroupLastQnaIsNull(@Param("lectureName")String lectureName);



    //학생 단일 검색 idx
    @Select("SELECT * FROM student where idx=#{idx}")
    Student selectStudentByIdx(@Param("idx") int idx);

    //학생 단일 검색 기본키(학생명,강의명)
    @Select("SELECT * FROM student where name=#{name} and lectureName=#{lectureName}")
    Student selectStudentByNameLcName(@Param("name") String name,@Param("lectureName") String lectureName);



    //학생 수정 qnaTimes
    @Update("UPDATE student SET qnaTimes = #{qnaTimes} where idx = #{idx}")
    int updateStudentQnaTimesByIdx(
            @Param("idx") int idx,
            @Param("qnaTimes") int qnaTimes
    );
    //학생 수정 lastQnaDate
    @Update("UPDATE student SET lastQnaDate = #{lastQnaDate} where name = #{name} and lectureName=#{lectureName}")
    int updateStudentLastQnaDate(
            @Param("name") String name,
            @Param("lectureName") String lectureName,
            @Param("lastQnaDate") Date lastQnaDate
    );

    //학생 삭제(idx)
    @Delete("DELETE FROM student where id = #{idx}")
    int deleteStudentById(@Param("idx")int idx);

    //학생 삭제(name,lectureName)
    @Delete("DELETE FROM student where name = #{name} and lectureName=#{lectureName}")
    int deleteStudentByNameLcName(@Param("name")String name, @Param("lectureName")String lectureName);


    //Qna CRUD QNA 삽입시마다 student.lastQnaDate update 필요함
    //qna 삽입
    @Insert("INSERT INTO qna(studentName, lectureName) VALUES(#{studentName},#{lectureName})")
    int insertQna(@Param("studentName") String studentName, @Param("lectureName") String lectureName);

    //qna 검색 질문시간 내림차순
    @Select("SELECT * FROM qna where studentName = #{studentName} and lectureName = #{lectureName} order by qnaTime desc")
    List<Qna> selectQnaByStdNameLcName(@Param("studentName") String studentName, @Param("lectureName") String lectureName);

    //최근 등록 qna 검색
    @Select("SELECT * FROM qna where studentName = #{studentName} and lectureName = #{lectureName} order by idx desc limit 1")
    Qna selectRecentQna(@Param("studentName") String studentName, @Param("lectureName") String lectureName);

    //qna 삭제
    @Delete("DELETE FROM qna where student_id = #{student_id} and lecture_name = #{lecture_name}")
    int delteQna(@Param("student_id")String student_id, @Param("lecture_name")String lecture_name);
}
