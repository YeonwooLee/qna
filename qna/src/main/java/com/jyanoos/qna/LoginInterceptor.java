package com.jyanoos.qna;


import com.jyanoos.qna.domain.QnaConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res ,Object handler) throws IOException {
        HttpSession httpSession = req.getSession();
        String requestURI = req.getRequestURI();
        if(httpSession==null||httpSession.getAttribute(QnaConst.LOGIN_MEMBER)==null){
            log.info("비로그인 요청 from {}",requestURI);
            res.sendRedirect("/loginpage");
            log.info("로그인 페이지로 이동");
            return false;
        }
        return true;
    }
}
