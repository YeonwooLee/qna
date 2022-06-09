package com.jyanoos.qna.controller;

import com.google.gson.Gson;
import com.jyanoos.qna.domain.Lecture;
import com.jyanoos.qna.domain.Professor;
import com.jyanoos.qna.domain.QnaConst;
import com.jyanoos.qna.domain.Student;
import com.jyanoos.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    @RequestMapping("/qnamain")
    public String qnaMain(Model model, HttpServletRequest req) throws IOException {
        HttpSession session = req.getSession();
        Professor professor = (Professor) session.getAttribute(QnaConst.LOGIN_MEMBER);
        log.info("professor 정보: {}",professor);
        String professorName = professor.getName();

        log.info("교수명의로 강의 리스트 생성 - 현재 교수명 : {}", professorName);
        List<Lecture> lectureList = qnaService.getLectureList(professorName);
        String firstLectureName = lectureList.get(0).getName();

        log.info("교수명으로 가져온 강의 리스트의 첫번째 강의 기준으로 메인 화면 구성용 학생 정보 수집 - 현재 강의명 {}",firstLectureName);
        List<List<Student>> studentListArr = qnaService.getStudents(firstLectureName);

        model.addAttribute("lectureList",lectureList);
        model.addAttribute("studentListArr",studentListArr);
        return "qnaMain";
    }

    @RequestMapping("/qnaTimesAjax")
    @ResponseBody
    public String qnaTimesAjax(Model model, @RequestParam("studentIdx") int studentIdx, @RequestParam("mode") String mode){
        log.info("ajax요청으로 받아온 idx = {}", studentIdx);
        Student student = qnaService.getStudentByIdx(studentIdx);
        int beforeQnaTimes = student.getQnaTimes(); //기존 qnaTimes
        int afterQnaTimes = student.getQnaTimes(); //변경용 qnaTimes

        //ajax요청의 mode인자를 보고 더하긴지 빼긴지 판별 후 반영
        if(mode.equals("plus")) afterQnaTimes+=1;
        if(mode.equals("minus")) afterQnaTimes-=1;

        student = qnaService.modifyQnaTimes(studentIdx,afterQnaTimes);

        Gson gson = new Gson();
        String studentJson = gson.toJson(student);
        log.info("{}의 질문 수 {}---->{}",student.getName(),beforeQnaTimes,afterQnaTimes);
        return studentJson;//json형태로 변환햏서 보내야할듯
    }
}
