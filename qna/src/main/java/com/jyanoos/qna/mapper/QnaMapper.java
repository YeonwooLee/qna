package com.jyanoos.qna.mapper;

import com.jyanoos.qna.domain.Lecture;
import com.jyanoos.qna.domain.Professor;
import com.jyanoos.qna.domain.Qna;
import com.jyanoos.qna.domain.Student;
import org.apache.ibatis.annotations.*;

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
    @Select("SELECT * FROM professor where id = #{id}")
    Professor selectProfessorById(
            @Param("id") String id
    );

    //교수 삭제
    @Delete("DELETE FROM professor where id = #{id}")
    int deleteProfessor(@Param("id")String id);




    //강의 CRUD
    //강의 삽입
    @Insert("INSERT INTO lecture(name, professor_id)" +
            "VALUES(#{name}, #{professor_id})")
    int insertLecture(
            @Param("name") String name,
            @Param("professor_id") String professor_id
    );

    //강의 검색
    @Select("SELECT * FROM lecture where professorName=#{professorName}")
    List<Lecture> selectLecturesByProfessorName(
            @Param("professorName") String professorName
    );

    @Select("select count(*) from lecture where name = #{name}")
    int counttLectureByName(@Param("name")String name);

    @Select("select * from lecture where name = #{name}")
    Lecture selectLectureByName(@Param("name")String name);

    //강의명 변경
    @Update("UPDATE lecture SET name=#{name_after} where name=#{name_before}")
    int updateLectureByName(@Param("name_before")String name_before, @Param("name_after")String name_after);

    //강의 삭제
    @Delete("DELETE FROM lecture WHERE name = #{name}")
    int deleteLectureById(
            @Param("name") String name
    );




    //학생 CRUD
    //학생 삽입
    @Insert("INSERT INTO student(id,name,  lecture_name)" +
            "VALUES(#{id},#{name}, #{lecture_name})")
    int insertStudent(
        @Param("id") String id,
        @Param("name") String name,
        @Param("lecture_name") String lecture_name
    );

    //학생 그룹 검색 lecture_name
    @Select("SELECT * FROM student where lectureName=#{lectureName}")
    List<Student> selectStudentsByLectureName(@Param("lectureName") String lectureName);

    //학생 단일 검색 idx
    @Select("SELECT * FROM student where idx=#{idx}")
    Student selectStudentByIdx(@Param("idx") int idx);


    //학생 수정 qna_times
    @Update("UPDATE student SET qna_times = #{qna_times} where id = #{id}")
    int updateStudentQnaTimesById(
            @Param("qna_times") int qna_times,
            @Param("id") String id
    );

    //학생 삭제(id)
    @Delete("DELETE FROM student where id = #{id}")
    int deleteStudentById(@Param("id")String id);
    //학생 삭제(name)
    @Delete("DELETE FROM student where name = #{name}")
    int deleteStudentByName(@Param("name")String name);


    //Qna CRUD
    //qna 삽입
    @Insert("INSERT INTO qna(student_id, lecture_name) VALUES(#{student_id},#{lecture_name})")
    int insertQna(@Param("student_id") String student_id, @Param("lecture_name") String lecture_name);

    //qna 검색
    @Select("SELECT * FROM qna where student_id = #{student_id} and lecture_name = #{lecture_name}")
    List<Qna> selectQnaByStdIdLecId(@Param("student_id") String student_id, @Param("lecture_name") String lecture_name);

    //qna 삭제
    @Delete("DELETE FROM qna where student_id = #{student_id} and lecture_name = #{lecture_name}")
    int delteQna(@Param("student_id")String student_id, @Param("lecture_name")String lecture_name);
}
