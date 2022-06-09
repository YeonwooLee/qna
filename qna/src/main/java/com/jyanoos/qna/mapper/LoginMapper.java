package com.jyanoos.qna.mapper;

import com.jyanoos.qna.domain.Professor;
import com.jyanoos.qna.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoginMapper {
    @Select("SELECT * FROM professor where name = #{loginId}")
    Professor selectProfessorByid(@Param("name")String id);
}