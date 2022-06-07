package com.jyanoos.qna.mapper;

import com.jyanoos.qna.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoginMapper {
    //insert문 실행(회원입력)
    @Insert("CREATE TABLE IF NOT EXISTS `check_time` (\n" +
            "                `page_name` VARCHAR(200) NOT NULL DEFAULT '' COMMENT '페이지 이름',\n" +
            "                `last_check` TIMESTAMP NULL COMMENT '저번 체크 타임',\n" +
            "                `second_last_check` TIMESTAMP NULL COMMENT '저저번 체크 타임',\n" +
            "                `error_time` TIMESTAMP NULL COMMENT '에러시간',\n" +
            "                `error_log` VARCHAR(15000) NOT NULL DEFAULT '' COMMENT '에러로그',\n" +
            "                PRIMARY KEY (`page_name`)\n" +
            "            )\n" +
            "            COLLATE='utf8mb4_unicode_ci'\n" +
            "            ")
    void saveUser();

    //select문 실행(회원조회)
    @Select("SELECT * FROM temp_user")
    List<User> listUser();


    //동적쿼리 ${}로 감싸면 ''포함 안되고, #{}로 감싸면 ''이 자동으로 포함됩니다
    @Select("SELECT * FROM temp_user where id=${id} AND email=#{email}")
    User showUser(@Param("id")int id, @Param("email")String email);
}