package bitcamp.java89.ems2.control.teacher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import bitcamp.java89.ems2.control.PageController;
import bitcamp.java89.ems2.dao.ManagerDao;
import bitcamp.java89.ems2.dao.MemberDao;
import bitcamp.java89.ems2.dao.StudentDao;
import bitcamp.java89.ems2.dao.TeacherDao;

@Controller("/teacher/delete.do")
public class TeacherDeleteControl implements PageController {
  @Autowired TeacherDao teacherDao;
  @Autowired MemberDao memberDao;
  @Autowired StudentDao studentDao;
  @Autowired ManagerDao managerDao;

  @Override
  public String service(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));
    
    if (!teacherDao.exist(memberNo)) {
      throw new Exception("강사를 찾지 못했습니다.");
    }
    
    teacherDao.delete(memberNo);
    
    if (!studentDao.exist(memberNo) && !managerDao.exist(memberNo)) {
      memberDao.delete(memberNo);
    }
    
    return "redirect:list.do";
  }  
}
