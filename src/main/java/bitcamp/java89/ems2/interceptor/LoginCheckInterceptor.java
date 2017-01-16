package bitcamp.java89.ems2.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import bitcamp.java89.ems2.domain.Member;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//  로그인 로그아웃을 요청한 경우 로그인 여부를 검사하지 않는다.
    HttpSession session = request.getSession();
    Member member = (Member)session.getAttribute("member");
    
    if (member == null) {// 로그인안되어 있으면 sendRedirect
      response.sendRedirect(request.getContextPath()+ "/auth/loginform.do");
      return false;
    }
    return true; //통과시킴
  }
}
