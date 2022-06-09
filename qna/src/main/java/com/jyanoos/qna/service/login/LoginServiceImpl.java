package com.jyanoos.qna.service.login;

import com.jyanoos.qna.domain.LoginResult;
import com.jyanoos.qna.domain.Professor;
import com.jyanoos.qna.mapper.LoginMapper;
import com.jyanoos.qna.mapper.QnaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final LoginMapper loginMapper;
    private  final QnaMapper qnaMapper;
    @Override
    public LoginResult loginBasic(String professorId, String professorPw) {
        log.info("기본 로그인 시작");
        LoginResult loginResult = new LoginResult(); //결과 담는 부분

        int professorExist = qnaMapper.countProfessorByname(professorId);
        if(professorExist==0) {
            loginResult.setSuccess(false);
            loginResult.setMessage("아이디 존재하지 않음");
            return loginResult;
        }

        Professor professor = qnaMapper.selectProfessorByname(professorId);
        log.info("professor = {}",professor);

        boolean success = (professor.getPassword().equals(professorPw)) ? true: false;
        loginResult.setSuccess(success);
        log.info("로그인 성공여부 = {}",success);

        if(success){
            loginResult.setProfessor(professor);
        }else{
            loginResult.setMessage("비밀번호 틀림");
        }

        return loginResult;
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }
}