package bitcamp.java89.ems2.servlet.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.java89.ems2.dao.MemberDao;
import bitcamp.java89.ems2.domain.Member;

@WebServlet("/auth/logout")
public class LogoutServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    request.getSession().invalidate(); // 기존 세션을 무효화 시킨다.
//    => 세션을 무효화시키는 순간 세션에 저장된 모든 데이터는 제거된다.
    
    response.sendRedirect("login"); // 웹브라우저에게 로그인 화면으로 이동할 것을 명령한다.
  }
}
