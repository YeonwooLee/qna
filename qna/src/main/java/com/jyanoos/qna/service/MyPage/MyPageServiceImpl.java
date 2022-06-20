package com.jyanoos.qna.service.MyPage;


import com.jyanoos.qna.domain.Professor;
import com.jyanoos.qna.mapper.ProfessorMapper;
import com.jyanoos.qna.mapper.QnaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{
    private final QnaMapper qnaMapper;
    private final ProfessorMapper professorMapper;
    @Override
    public boolean regiPgTitle(String professorName, String pageTitle, String pageSubTitle, String pageFooterMsg) {
        int i = professorMapper.updateProfessorPage(professorName, pageTitle, pageSubTitle, pageFooterMsg);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public Professor getProfessor(String professorName) {
        Professor professor = qnaMapper.selectProfessorByname(professorName);
        return professor;
    }

    @Override
    public boolean modPw(String professorName, String pw) {
        int i = professorMapper.updateProfessorPw(professorName, pw);
        if(i==1) return true;
        else return false;

    }
}
