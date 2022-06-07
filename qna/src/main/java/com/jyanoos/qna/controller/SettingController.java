package com.jyanoos.qna.controller;

import com.jyanoos.qna.service.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SettingController {
    private final SettingService settingService;

    @RequestMapping("/setting")
    @ResponseBody
    public String setting(){
        settingService.CrtTbl();
        return "setting";
    }


}
