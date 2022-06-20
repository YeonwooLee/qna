package com.jyanoos.qna.controller;
import com.jyanoos.qna.domain.LoginResult;
import com.jyanoos.qna.domain.Professor;
import com.jyanoos.qna.domain.QnaConst;
import com.jyanoos.qna.service.login.LoginServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class LoginController {
    private final LoginServiceImpl loginService;

    public LoginController(LoginServiceImpl loginService) {
        this.loginService = loginService;
    }

    @RequestMapping("/loginpage{loginTry}")
    public String loginPage(Model model, @PathVariable("loginTry")String loginTry,HttpServletRequest req){
        HttpSession session = req.getSession(); //세션없으면 만듦
        if(session.getAttribute(QnaConst.LOGIN_MEMBER)!=null){
            return "redirect:/qnamain";
        }

        log.info("loginTry = {}",loginTry);
        model.addAttribute("loginTry",loginTry);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("loginId")String loginId, @RequestParam("password")String password, HttpServletRequest req, Model model){
        log.info("로그인 시작");
        HttpSession session = req.getSession(); //세션없으면 만듦
        LoginResult loginResult = loginService.loginBasic(loginId, password);
        log.info("로그인정보: {}",loginResult);

        Professor loginProfessor = loginResult.getProfessor();
        if(loginResult.isSuccess()){
            session.setAttribute(QnaConst.LOGIN_MEMBER,loginProfessor.getName());
            log.info("교수 로그인 id={}, name={}",loginProfessor.getIdx(),loginProfessor.getName());
            return "redirect:/qnamain";
        }else{
            return "redirect:/loginpagefalse";
        }

    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest req){
        HttpSession session = req.getSession();
        String professorName = (String) session.getAttribute(QnaConst.LOGIN_MEMBER);
        log.info("로그아웃 요청:{}",professorName);

        loginService.logout(session);
        return "redirect:/loginpage";
    }
}