package com.kh.myproject.member.manager.config;

import com.kh.myproject.member.user.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 특정 페이지 경로를 확인하고 세션 값 검사 및 처리 로직
        String requestURI = request.getRequestURI();

//         manager를 포함하는 경로를 검사하되, maager/home 경로는 별도로 처리한다. (managerController에 예외처리 구현돼있음)
//         혹은 UserController에서 처음부터 session을 설정하고 넘겨도 된다.
        if (requestURI.contains("/manager") && !requestURI.contains("/manager/home")) { // 특정 페이지 경로를 검사한다.

            // 인터셉터 처음 감지시에는 세션값이 없기 때문에 감지시에는 당연히 세션값이 없을 것이다.
            User manager = (User) request.getSession().getAttribute("manager");

            if (manager == null) {
                // 세션에 관리자 정보가 없을 경우 리다이렉트
                response.sendRedirect("/errorPage");
                return false; // 요청 중단
            }
        }

        return true; // 요청 계속 진행
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // 핸들러 메서드가 실행된 후 처리
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        // 요청 처리 완료 후 호출
    }
}