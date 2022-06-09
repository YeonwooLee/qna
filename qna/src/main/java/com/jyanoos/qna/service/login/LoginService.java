package com.jyanoos.qna.service.login;

import com.jyanoos.qna.domain.LoginResult;

import javax.servlet.http.HttpSession;

public interface LoginService {
    LoginResult loginBasic(String professorId, String professorPw);

    void logout(HttpSession session);
}
