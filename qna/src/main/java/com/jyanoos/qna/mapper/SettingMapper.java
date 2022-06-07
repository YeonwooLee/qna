package com.jyanoos.qna.mapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SettingMapper {

    @Insert("CREATE TABLE IF NOT EXISTS `professor` (\n" +
            "\t`idx` INT(11) NOT NULL AUTO_INCREMENT COMMENT '교수 인덱스',\n" +
            "\t`id` VARCHAR(50) NOT NULL COMMENT '교수 아이디' COLLATE 'utf8mb4_unicode_ci',\n" +
            "\t`name` VARCHAR(50) NOT NULL COMMENT '교수 성명' COLLATE 'utf8mb4_unicode_ci',\n" +
            "\t`password` VARCHAR(50) NOT NULL COMMENT '교수 비밀번호' COLLATE 'utf8mb4_unicode_ci',\n" +
            "\tPRIMARY KEY (`id`) USING BTREE,\n" +
            "\tUNIQUE INDEX `idx` (`idx`) USING BTREE\n" +
            ")\n" +
            "COLLATE='utf8mb4_unicode_ci'\n" +
            "ENGINE=InnoDB")
    void crtTblProfessor();//교수 테이블 생성

    @Insert("CREATE TABLE IF NOT EXISTS `lecture` (\n" +
            "\t`idx` INT(11) NOT NULL AUTO_INCREMENT COMMENT '강의 인덱스',\n" +
            "\t`name` VARCHAR(50) NOT NULL COMMENT '강의명' COLLATE 'utf8mb4_unicode_ci',\n" +
            "\t`professor_id` VARCHAR(50) NOT NULL DEFAULT ' ' COMMENT '강의 교수' COLLATE 'utf8mb4_unicode_ci',\n" +
            "\tPRIMARY KEY (`name`, `professor_id`) USING BTREE,\n" +
            "\tUNIQUE INDEX `idx` (`idx`) USING BTREE,\n" +
            "\tINDEX `professor_id` (`professor_id`) USING BTREE,\n" +
            "\tCONSTRAINT `lecture_ibfk_1` FOREIGN KEY (`professor_id`) REFERENCES `class_qna`.`professor` (`id`) ON UPDATE CASCADE ON DELETE CASCADE\n" +
            ")")
    void crtTblLecture();//강의 테이블 생성


    @Insert("CREATE TABLE IF NOT EXISTS `student` (\n" +
            "\t`idx` INT(11) NOT NULL AUTO_INCREMENT COMMENT '학생 기본 인덱스',\n" +
            "\t`name` VARCHAR(50) NOT NULL COMMENT '학생 성명' COLLATE 'utf8mb4_unicode_ci',\n" +
            "\t`qna_times` INT(11) NOT NULL DEFAULT '0' COMMENT '학생 질의응답 수',\n" +
            "\t`lecture_name` VARCHAR(50) NOT NULL COMMENT '학생 수업' COLLATE 'utf8mb4_unicode_ci',\n" +
            "\t`professor_id` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',\n" +
            "\tPRIMARY KEY (`name`, `lecture_name`, `professor_id`) USING BTREE,\n" +
            "\tUNIQUE INDEX `idx` (`idx`) USING BTREE,\n" +
            "\tUNIQUE INDEX `name` (`name`) USING BTREE,\n" +
            "\tUNIQUE INDEX `id` (`id`) USING BTREE,\n" +
            "\tINDEX `lecture_name` (`lecture_name`) USING BTREE,\n" +
            "\tINDEX `FK_student_professor` (`professor_id`) USING BTREE,\n" +
            "\tCONSTRAINT `FK_student_professor` FOREIGN KEY (`professor_id`) REFERENCES `class_qna`.`professor` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,\n" +
            "\tCONSTRAINT `student_ibfk_1` FOREIGN KEY (`lecture_name`) REFERENCES `class_qna`.`lecture` (`name`) ON UPDATE CASCADE ON DELETE CASCADE\n" +
            ")")
    void crtTblStudent();//학생 테이블 생성

    @Insert("\n" +
            "CREATE TABLE IF NOT EXISTS `qna` (\n" +
            "\t`idx` INT(11) NOT NULL AUTO_INCREMENT COMMENT '기본 인덱스',\n" +
            "\t`student_id` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',\n" +
            "\t`lecture_name` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',\n" +
            "\t`time` TIMESTAMP NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),\n" +
            "\t`content` VARCHAR(200) NULL DEFAULT NULL COLLATE 'utf8mb4_unicode_ci',\n" +
            "\tUNIQUE INDEX `idx` (`idx`) USING BTREE,\n" +
            "\tPRIMARY KEY(`idx`),\n" +
            "\tFOREIGN KEY(`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
            "\tFOREIGN KEY(`lecture_name`) REFERENCES `lecture` (`name`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
            ")\n" +
            "COLLATE='utf8mb4_unicode_ci'\n" +
            "ENGINE=InnoDB")
    void crtTblQna();//qna 테이블 생성

}
