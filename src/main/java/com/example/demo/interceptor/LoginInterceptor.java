package com.example.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final String LOGIN = "loginMember";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        ModelMap modelMap = modelAndView.getModelMap();
        Object member = modelMap.get("member");
        if (member != null) {
            // 로그인 성공시, Session 에 저장 후, 초기 화면으로 이동
            session.setAttribute(LOGIN, member);
            response.sendRedirect("/");
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        Object member = session.getAttribute(LOGIN);
        if (member != null) {
            return true;
        } else {
            // 로그인이 되어 있지 않으면, 로그인 후, 이전 경로로 리다이렉트 함
            String destUri = request.getRequestURI();
            String destQuery = request.getQueryString();
            String dest = (destQuery == null) ? destUri : destUri + "?" + destQuery;
            request.getSession().setAttribute("dest", dest);
            response.sendRedirect("/members/login");
            return false;
        }
    }
}
