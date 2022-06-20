package com.jyanoos.qna.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jyanoos.qna.domain.*;
import com.jyanoos.qna.service.MyPage.MyPageService;
import com.jyanoos.qna.service.qna.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;
    private final MyPageService myPageService;

    //qnamain의 주 컨텐츠 화면 구성(기본)
    @RequestMapping("/qnamain")
    public String qnaMain(Model model, HttpServletRequest req) throws IOException {
        HttpSession session = req.getSession();
        String professorName = (String) session.getAttribute(QnaConst.LOGIN_MEMBER);



        log.info("교수명의로 강의 리스트 생성 - 현재 교수명 : {}", professorName);
        List<Lecture> lectureList = qnaService.getLectureList(professorName);
        if(lectureList.size()==0){
            return "welcome";
        }
        log.info("qnamain부분 lectureList {}",lectureList);
        String firstLectureName = URLEncoder.encode(lectureList.get(0).getName(),"UTF-8");

        log.info("firstLectureName = {}",firstLectureName);
        return "redirect:/qnamain/"+firstLectureName;
//        log.info("교수명으로 가져온 강의 리스트의 첫번째 강의 기준으로 메인 화면 구성용 학생 정보 수집 - 현재 강의명 {}",firstLectureName);
//        List<List<Student>> studentListArr = qnaService.getStudents(firstLectureName);
//
//        model.addAttribute("lectureList",lectureList);
//        model.addAttribute("studentListArr",studentListArr);
//        return "qnaMain";
    }

    //qnamain의 주 컨텐츠 화면 구성(강의 선택)
    @RequestMapping("/qnamain/{lectureName}")
    public String qnaMain(Model model, HttpServletRequest req, @PathVariable("lectureName")String lectureName) throws IOException {
        if(lectureName.contains("addLcFail_")){
            String failLCName = lectureName.split("_")[1];
            model.addAttribute("addLcFail","true");//강의등록 실패로 들어온경우
            model.addAttribute("failLcName",failLCName);//강의등록 실패로 들어온경우
        }
        HttpSession session = req.getSession();
        String professorName = (String) session.getAttribute(QnaConst.LOGIN_MEMBER);
        Professor professor = myPageService.getProfessor(professorName);
        model.addAttribute("pageTitle",professor.getPageTitle());
        model.addAttribute("pageSubTitle",professor.getPageSubTitle());
        model.addAttribute("pageFooterMsg",professor.getPageFooterMsg());


        log.info("현재 교수명 : {}", professorName);
        List<Lecture> lectureList = qnaService.getLectureList(professorName);
        log.info("lectureList {}",lectureList);
        
        
        //강의 없는거면 첫강의로 이동
        boolean exist = false;
        for(int i=0;i<lectureList.size();i++){
            if(lectureList.get(i).getName().equals(lectureName)){
                exist = true;
            }
        }
        if(!exist) return "redirect:/qnamain";


        if (lectureList.size()==0){
            return "welcome";
        }


        log.info("현재 강의명 {}",lectureName);
        List<List<Student>> studentListArr = qnaService.getStudents(lectureName,professorName);
        if(studentListArr==null){
            log.info("studentListArr is null");
        }


        model.addAttribute("lectureList",lectureList);
        model.addAttribute("studentListArr",studentListArr);
        model.addAttribute("nowLecture",lectureName);
        return "qnaMain";
    }

    //강의추가
    @GetMapping("/qnamain/addLecture/{newLectureName}")
    String addLecture(@PathVariable("newLectureName")String newLectureName,
                      HttpServletRequest req){//강의추가
        HttpSession session = req.getSession();
        String professorName = (String) session.getAttribute(QnaConst.LOGIN_MEMBER);


        Lecture lecture = qnaService.addLecture(professorName, newLectureName);
        if(lecture==null){
            return "redirect:/qnamain/addLcFail_"+newLectureName;
        }

        return "redirect:/qnamain/"+newLectureName;

    }
    //강의추가
    @PostMapping("/qnamain/addLecture")
    String addLectureFirst(@RequestParam("newLectureName")String newLectureName,
                      HttpServletRequest req){//강의추가
        HttpSession session = req.getSession();
        String professorName = (String) session.getAttribute(QnaConst.LOGIN_MEMBER);
        qnaService.addLecture(professorName,newLectureName);
        return "redirect:/qnamain";

    }
    //ajax로 질문 수 변경
    @RequestMapping("/qnaTimesAjax")
    @ResponseBody
    public String qnaTimesAjax(Model model, @RequestParam("studentIdx") int studentIdx, @RequestParam("mode") String mode,HttpServletRequest req){
        log.info("ajax요청으로 받아온 idx = {}", studentIdx);
        HttpSession session = req.getSession();
        String professorName = (String) session.getAttribute(QnaConst.LOGIN_MEMBER);
        Student student = qnaService.getStudentByIdx(studentIdx);
        int beforeQnaTimes = student.getQnaTimes(); //기존 qnaTimes
        int afterQnaTimes = student.getQnaTimes(); //변경용 qnaTimes

        //ajax요청의 mode인자를 보고 더하긴지 빼긴지 판별 후 반영
        if(mode.equals("plus")) {
            afterQnaTimes+=1;
            Qna qna = qnaService.addQna(qnaService.getStudentByIdx(studentIdx).getName(), qnaService.getStudentByIdx(studentIdx).getLectureName(),professorName);
        }
        if(mode.equals("minus")) afterQnaTimes-=1;

        student = qnaService.modifyQnaTimes(studentIdx,afterQnaTimes);
        log.info("student 정보 --->{}",student);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        String studentJson = gson.toJson(student);
        log.info("{}의 질문 수 {}---->{}",student.getName(),beforeQnaTimes,afterQnaTimes);
        return studentJson;//json형태로 변환햏서 보내야할듯
    }
    //ajax로 학생pick
    @PostMapping("/qnamain/pick")
    @ResponseBody
    public String picStd(@RequestParam("lectureName") String lectureName, @RequestParam("mode") String mode,HttpServletRequest req){
        HttpSession session = req.getSession();
        String professorName = (String) session.getAttribute(QnaConst.LOGIN_MEMBER);
        Student student = qnaService.pickQnaStudent(lectureName, mode,professorName);
        Gson gson = new Gson();

        String studentJson = gson.toJson(student);

        return studentJson;
    }
    //학생 단체 등록
    @RequestMapping("/qnamain/regiStdLst")
    public String regiStdLst(@RequestParam("nowLecture")String nowLecture,HttpServletRequest req,@RequestParam("studentArr") List<String> studentArr) throws UnsupportedEncodingException {
        log.info("학생 단체등록 시작");
        HttpSession session = req.getSession();
        String professorName = (String) session.getAttribute(QnaConst.LOGIN_MEMBER);

        //String[] studentArr = stdList.split("\n");//textarea 값 가져와서 split
        //String[] studentArr = stdList.split(" ");//textarea 값 가져와서 split

        log.info("studentArr {}",studentArr);

        List<Student> studentList = qnaService.addStdList(studentArr, nowLecture, professorName);
        log.info("학생 단체 등록 완료 강의-{} 로 이동",nowLecture);
        String redirectURI = "/qnamain/"+ URLEncoder.encode(nowLecture,"UTF-8");
        log.info("redirectURI = {}",redirectURI);
        return "redirect:"+redirectURI;
    }
    //학생삭제
    @RequestMapping("/qnamain/delstudent/{nowLecture}/{stdName}")
    public String delStudent(@PathVariable("nowLecture") String nowLecture, @PathVariable("stdName") String stdName,HttpServletRequest req){

        log.info("학생 삭제 요청 stdName = {}, lectureName = {}",stdName,nowLecture);
        HttpSession session = req.getSession();
        String professorName = (String) session.getAttribute(QnaConst.LOGIN_MEMBER);

        qnaService.removeStudent(nowLecture,stdName,professorName);
        return "redirect:/qnamain/"+nowLecture;
    }
    //강의삭제
    @RequestMapping("/qnamain/del_lecture/{removeLectureName}/{nowLecture}")
    public String removeLecture(
            @PathVariable("removeLectureName")String removeLectureName,
            @PathVariable("nowLecture")String nowLecture,
            HttpServletRequest req){
        log.info("{} 삭제요청",removeLectureName);
        HttpSession session = req.getSession();
        String professorName = (String) session.getAttribute(QnaConst.LOGIN_MEMBER);
        qnaService.removeLecture(removeLectureName,professorName);
        if(removeLectureName.equals(nowLecture)) return "redirect:/qnamain";
        else return "redirect:/qnamain/"+nowLecture;
    }
    //ajax로 학생중복확인
    @PostMapping("/check/sameStd")
    @ResponseBody
    public String checkExistStd(@RequestParam("studentNames") List<String> studentArr,@RequestParam("lectureName") String lectureName, HttpServletRequest req){
        HttpSession session = req.getSession();
        //log.info("studende  {}",studentNames);
        String professorName = (String) session.getAttribute(QnaConst.LOGIN_MEMBER);
        List<Student> studentList = qnaService.getStudentsNoLine(lectureName,professorName);//현재학생목록
        //String[] studentArr = studentNames.split("\n");//추가할 학생목록
        log.info("StdArr {}",studentArr);

        Map<String,String> map = new HashMap<>();
        Gson gson = new Gson();

        for(int i=0;i<studentList.size();i++){
            if(studentArr.contains(studentList.get(i).getName())) {
                map.put("exist","true");
                map.put("existStdName",studentList.get(i).getName());
                String result = gson.toJson(map);
                return result;
            }
        }
        map.put("exist","false");
        String result = gson.toJson(map);
        return result;

    }

}
