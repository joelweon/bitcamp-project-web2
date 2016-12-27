package bitcamp.java89.ems2.servlet.teacher;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.java89.ems2.dao.impl.ManagerMysqlDao;
import bitcamp.java89.ems2.dao.impl.MemberMysqlDao;
import bitcamp.java89.ems2.dao.impl.StudentMysqlDao;
import bitcamp.java89.ems2.dao.impl.TeacherMysqlDao;
import bitcamp.java89.ems2.listener.ContextLoaderListener;

@WebServlet("/teacher/delete")
public class TeacherDeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    
    try {
      int memberNo = Integer.parseInt(request.getParameter("memberNo"));
      
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<meta name='viewport' content='width=device-width, user-scalable=no'>");
      out.println("<meta http-equiv='Refresh' content='1;url=list'>");
      out.println("<title>강사관리-삭제</title>");
      out.println("</head>");
      out.println("<body>");
      
//      HeaderServlet에게 머리말(header) HTML 생성을 요청한다. 
      RequestDispatcher rd = request.getRequestDispatcher("/header");
      rd.include(request, response);
      
      
      out.println("<h1>삭제 결과</h1>");
      
      
      TeacherMysqlDao teacherDao = (TeacherMysqlDao)ContextLoaderListener.applicationContext.getBean("teacherDao");
      
      if (!teacherDao.exist(memberNo)) {
        throw new Exception("강사를 찾지 못했습니다.");
      }
      
      teacherDao.delete(memberNo);
      
      MemberMysqlDao memberDao = (MemberMysqlDao)ContextLoaderListener.applicationContext.getBean("memberDao");
      ManagerMysqlDao managerDao = (ManagerMysqlDao)ContextLoaderListener.applicationContext.getBean("managerDao");
      StudentMysqlDao studentDao = (StudentMysqlDao)ContextLoaderListener.applicationContext.getBean("studentDao");
      
      
      if (!managerDao.exist(memberNo) && !studentDao.exist(memberNo)) {
        memberDao.delete(memberNo);
      }
      
      out.println("<p>삭제하였습니다.</p>");
      
//      FooterServlet에게 꼬리말 HTML 생성을 요청한다.
      rd = request.getRequestDispatcher("/footer");
      rd.include(request, response);
      
      out.println("</body>");
      out.println("</html>");
      
    } catch (Exception e) {
      request.setAttribute("error", e);
      RequestDispatcher rd = request.getRequestDispatcher("/error");
      rd.forward(request, response);
      return;
    }
  }
}
