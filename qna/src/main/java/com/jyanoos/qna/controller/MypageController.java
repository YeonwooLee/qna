package com.jyanoos.qna.controller;

import com.jyanoos.qna.domain.Professor;
import com.jyanoos.qna.domain.QnaConst;
import com.jyanoos.qna.service.MyPage.MyPageService;
import com.jyanoos.qna.service.qna.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage/*")
public class MypageController {
    private final QnaService qnaService;
    private final MyPageService myPageService;

    @RequestMapping("/main/{loginUser}")
    public String myPageMain(Model model, HttpServletRequest req, @PathVariable("loginUser") String loginUser){
        HttpSession session = req.getSession();
        String professorName = (String) session.getAttribute(QnaConst.LOGIN_MEMBER);
        Professor professor = myPageService.getProfessor(loginUser);//로그인유저명으로 교수 가져옴


        model.addAttribute("professorName",professor.getName());
        model.addAttribute("pageTitle",professor.getPageTitle());
        model.addAttribute("pageSubTitle",professor.getPageSubTitle());
        model.addAttribute("pageFooterMsg",professor.getPageFooterMsg());
        return "myPage/mypage";
    }

    @PostMapping("/update/{loginUser}")
    public String regiPgTitle(
            HttpServletRequest req,
            @RequestParam("pageTitle") String pageTitle,
            @RequestParam("pageSubTitle") String pageSubTitle,
            @RequestParam("pageFooterMsg") String pageFooterMsg,
            @RequestParam("pw") String pw,
            @RequestParam("pwChk") String pwChk){
        HttpSession session = req.getSession();
        String professorName = (String) session.getAttribute(QnaConst.LOGIN_MEMBER);
        myPageService.regiPgTitle(professorName,pageTitle,pageSubTitle,pageFooterMsg);
        log.info(String.valueOf(pw.equals(pwChk)));
        log.info(String.valueOf(pw.equals("")));
        if(pw.equals(pwChk)&&!pw.equals("")){
            log.info("비밀번호 수정요청:{}",pw);
            myPageService.modPw(professorName,pw);
        }
        return "redirect:/mypage/main/"+professorName;
    }
}
