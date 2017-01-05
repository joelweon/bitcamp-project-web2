package bitcamp.java89.ems2.control.student;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import bitcamp.java89.ems2.control.PageController;
import bitcamp.java89.ems2.dao.StudentDao;
import bitcamp.java89.ems2.domain.Student;

@Controller("/student/list.do")
public class StudentListControl implements PageController {
  @Autowired
  StudentDao studentDao;

  @Override
  public String service(HttpServletRequest request, HttpServletResponse response) throws Exception {

    ArrayList<Student> list = studentDao.getList();
    request.setAttribute("students", list);
    request.setAttribute("title", "학생관리-목록");
    request.setAttribute("contentPage", "/student/list.jsp");
    return "/main.jsp";
  }
}
/*
스프링 IoC에서 관리하는 순간 AutoWired를 사용할 수 있다.
StudentDao를 직접 꺼내는 게 아니라 
    StudentDao studentDao = 
        (StudentDao)ContextLoaderListener.applicationContext.getBean("studentDao");
자동으로 의존객체를 꽃아준다.

*/