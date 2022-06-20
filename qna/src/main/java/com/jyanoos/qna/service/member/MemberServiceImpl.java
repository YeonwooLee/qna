package com.jyanoos.qna.service.member;

import com.jyanoos.qna.domain.Professor;
import com.jyanoos.qna.mapper.MemberMapper;
import com.jyanoos.qna.mapper.QnaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberMapper memberMapper;
    private final QnaMapper qnaMapper;
    @Override
    public boolean overlapChk(String pfId) {
        int count = memberMapper.selectPf(pfId);

        if(count==1) return true;
        else return false;

    }

    @Override
    public Professor joinPf(String pfId, String pw, String pfHumanName){
        int i = memberMapper.insertProfessor(pfId, pw, pfHumanName);

        Professor professor = qnaMapper.selectProfessorByname(pfId);
        return professor;



    }
}
