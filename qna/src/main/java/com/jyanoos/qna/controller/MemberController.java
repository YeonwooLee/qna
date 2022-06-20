package com.jyanoos.qna.controller;


import com.google.gson.Gson;
import com.jyanoos.qna.domain.Professor;
import com.jyanoos.qna.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/*")
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/overlapChk")
    @ResponseBody
    public String sameProfessor(@RequestParam("pfId") String pfId){
        log.info("pf id is {}",pfId);
        String pfIdExist = memberService.overlapChk(pfId)?"true":"false"; //아이디 유무 확인
        log.info("pfExists is {}",pfIdExist);
        Map<String,String> map = new HashMap<>();
        Gson gson = new Gson();

        map.put("pfId",pfId);
        map.put("exist",pfIdExist);

        String result = gson.toJson(map);
        log.info("result is {}",result);
        return result;
    }

    @GetMapping("/join")
    public String joinForm(){
        return "/join";
    }

    @PostMapping("/join")
    public String joinReq(@RequestParam("id") String id, @RequestParam("pw")String pw, @RequestParam("humanName")String humanName, Model model){
        Professor professor = memberService.joinPf(id, pw, humanName);
        Map<String,String> infos = new HashMap<>();
        infos.put("id",id);
        infos.put("humanName",humanName);
        model.addAttribute("infos",infos);
        return "/joinDone";
    }
}
