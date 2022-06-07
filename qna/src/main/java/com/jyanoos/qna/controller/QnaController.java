package com.jyanoos.qna.controller;

import com.jyanoos.qna.domain.Lecture;
import com.jyanoos.qna.domain.Student;
import com.jyanoos.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QnaController {
    private final String professorName = "admin";
    private final QnaService qnaService;

    @RequestMapping("/qnamain")
    public String qnaMain(Model model) throws IOException {
        log.info("교수명의로 강의 리스트 생성 - 현재 교수명 : {}", professorName);
        List<Lecture> lectureList = qnaService.getLectureList(professorName);
        String firstLectureName = lectureList.get(0).getName();

        log.info("교수명으로 가져온 강의 리스트의 첫번째 강의 기준으로 메인 화면 구성용 학생 정보 수집 - 현재 강의명 {}",firstLectureName);
        List<List<Student>> studentListArr = qnaService.getStudents(firstLectureName);

        model.addAttribute("lectureList",lectureList);
        model.addAttribute("studentListArr",studentListArr);
        return "qnaMain";
    }
}
