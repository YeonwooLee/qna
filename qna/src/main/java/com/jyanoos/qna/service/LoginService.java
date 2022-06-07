package com.jyanoos.qna.service;

import com.jyanoos.qna.domain.User;
import com.jyanoos.qna.mapper.LoginMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class LoginService {
    private final LoginMapper loginMapper;

    public LoginService(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    //회원입력
    public void joinUser(){
        loginMapper.saveUser();

    }

    //회원리스트조회
    public List<User> showUser(){
        return loginMapper.listUser();
    }

    //회원조회
    public User showOneUser(int id, String email){
        return loginMapper.showUser(id,email);
    }

}