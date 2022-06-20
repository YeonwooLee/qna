package com.jyanoos.qna.service.member;

import com.jyanoos.qna.domain.Professor;

public interface MemberService {
    //ajax ID중복확인
    boolean overlapChk(String pfId);
    
    //교수등록
    Professor joinPf(String pfId, String pw, String pfHumanName);
}
