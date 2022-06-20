package com.jyanoos.qna.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {
    @Select("select count(*) from professor where name=#{name}")
    int selectPf(@Param("name") String name);

    //교수 삽입
    @Insert("INSERT INTO professor(name, password, humanName)" +
            "VALUES(#{name},#{password},#{humanName})")
    int insertProfessor(
            @Param("name") String name,
            @Param("password") String password,
            @Param("humanName") String humanName
    );
}
