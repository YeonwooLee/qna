package com.jyanoos.qna.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SettingServiceTest {
    @Autowired
    SettingService settingService;

    @Test
    void 세팅테스트(){
        settingService.CrtTbl();
    }

}
