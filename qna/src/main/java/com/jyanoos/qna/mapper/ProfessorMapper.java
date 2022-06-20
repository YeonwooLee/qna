package com.jyanoos.qna.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProfessorMapper {

    @Update("UPDATE professor SET pageTitle=#{pageTitle}, pageSubTitle=#{pageSubTitle}, pageFooterMsg=#{pageFooterMsg} WHERE name=#{prfessorName}")
    int updateProfessorPage(
            @Param("prfessorName") String prfessorName,
            @Param("pageTitle") String pageTitle,
            @Param("pageSubTitle") String pageSubTitle,
            @Param("pageFooterMsg") String pageFooterMsg
    );
    @Update("update professor set password=#{pw} where name=#{professorName}")
    int updateProfessorPw(@Param("professorName") String professorName, @Param("pw") String pw);
}
