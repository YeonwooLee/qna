package com.jyanoos.qna.controller;
import com.jyanoos.qna.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import com.jyanoos.qna.domain.User;
@Controller
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping("/test")
    public String join(Model model){
        loginService.joinUser(); //회원 입력
        List<User> users = loginService.showUser(); //회원조회
        model.addAttribute("users",users);


        int id = 1223;
        String email = "a3a@aa.a";
        User oneUser = loginService.showOneUser(id,email);
        model.addAttribute("oneUser",oneUser);

        return "login";
    }
}