package com.jyanoos.qna.service;

import com.jyanoos.qna.mapper.SettingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SettingServiceImpl implements SettingService{
    private final SettingMapper settingMapper;


    @Override
    public boolean CrtTbl() {
        log.info("필수 테이블 생성");

        log.info("create tbl Professor");
        settingMapper.crtTblProfessor();

        log.info("create tbl Lecture");
        settingMapper.crtTblLecture();

        log.info("create tbl Student");
        settingMapper.crtTblStudent();

        log.info("create tbl Qna");
        settingMapper.crtTblQna();
        return true;
    }
}
