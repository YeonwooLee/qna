package com.jyanoos.qna.service.MyPage;

import com.jyanoos.qna.domain.Professor;

public interface MyPageService {
    //교수명으로 페이지 컨텐츠 등록
    public boolean regiPgTitle(String professorName, String pageTitle, String pageSubTitle, String pageFooterMsg);

    //교수선택(이름)
    public Professor getProfessor(String professorName);

    //비번수정
    public boolean modPw(String professorName,String pw);
}
