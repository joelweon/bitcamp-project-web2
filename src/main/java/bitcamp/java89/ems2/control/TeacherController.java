package bitcamp.java89.ems2.control;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import bitcamp.java89.ems2.anotation.RequestMapping;
import bitcamp.java89.ems2.dao.ManagerDao;
import bitcamp.java89.ems2.dao.MemberDao;
import bitcamp.java89.ems2.dao.StudentDao;
import bitcamp.java89.ems2.dao.TeacherDao;
import bitcamp.java89.ems2.domain.Member;
import bitcamp.java89.ems2.domain.Photo;
import bitcamp.java89.ems2.domain.Teacher;
import bitcamp.java89.ems2.util.MultipartUtil;

@Controller
public class TeacherController {
  @Autowired TeacherDao teacherDao;
  @Autowired MemberDao memberDao;
  @Autowired ManagerDao managerDao;
  @Autowired StudentDao studentDao;
  
  @RequestMapping("/teacher/list.do")
  public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ArrayList<Teacher> list = teacherDao.getList();
    request.setAttribute("teachers", list);
    request.setAttribute("title", "강사관리-목록");
    request.setAttribute("contentPage", "/teacher/list.jsp");
    return "/main.jsp";
  }
  
  @RequestMapping("/teacher/detail.do")
  public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));
    
    Teacher teacher = teacherDao.getOne(memberNo);
    
    if (teacher == null) {
      throw new Exception("해당 강사가 없습니다.");
    }
    
    
    request.setAttribute("teacher", teacher);
    return "/teacher/detail.jsp";
    
  }
  
  @RequestMapping("/teacher/add.do")
  public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    Map<String,String> dataMap = MultipartUtil.parse(request);
    
    Teacher teacher = new Teacher();
    teacher.setEmail(dataMap.get("email"));
    teacher.setPassword(dataMap.get("password"));
    teacher.setName(dataMap.get("name"));
    teacher.setTel(dataMap.get("tel"));
    teacher.setHomepage(dataMap.get("homepage"));
    teacher.setFacebook(dataMap.get("facebook"));
    teacher.setTwitter(dataMap.get("twitter"));
    
    ArrayList<Photo> photoList = new ArrayList<>();
    photoList.add(new Photo(dataMap.get("photoPath1")));
    photoList.add(new Photo(dataMap.get("photoPath2")));
    photoList.add(new Photo(dataMap.get("photoPath3")));
    
    teacher.setPhotoList(photoList);
    
    if (teacherDao.exist(teacher.getEmail())) {
      throw new Exception("이메일이 존재합니다. 등록을 취소합니다.");
    }
    
    
    if (!memberDao.exist(teacher.getEmail())) { // 학생이나 매니저로 등록되지 않았다면,
      memberDao.insert(teacher);
      
    } else { // 학생이나 매니저로 이미 등록된 사용자라면 기존의 회원 번호를 사용한다.
      Member member = memberDao.getOne(teacher.getEmail());
      teacher.setMemberNo(member.getMemberNo());
    }
    
    teacherDao.insert(teacher);
    return "redirect:list.do";
  }
  
  @RequestMapping("/teacher/delete.do")
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
  
  @RequestMapping("/teacher/update.do")
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map<String,String> dataMap = MultipartUtil.parse(request);
    
    Teacher teacher = new Teacher();
    teacher.setMemberNo(Integer.parseInt(dataMap.get("memberNo")));
    teacher.setEmail(dataMap.get("email"));
    teacher.setPassword(dataMap.get("password"));
    teacher.setName(dataMap.get("name"));
    teacher.setTel(dataMap.get("tel"));
    teacher.setHomepage(dataMap.get("homepage"));
    teacher.setFacebook(dataMap.get("facebook"));
    teacher.setTwitter(dataMap.get("twitter"));
    
    ArrayList<Photo> photoList = new ArrayList<>();
    photoList.add(new Photo(dataMap.get("photoPath1")));
    photoList.add(new Photo(dataMap.get("photoPath2")));
    photoList.add(new Photo(dataMap.get("photoPath3")));
    
    teacher.setPhotoList(photoList);
    
    
    if (!teacherDao.exist(teacher.getMemberNo())) {
      throw new Exception("강사를 찾지 못했습니다.");
    }
    
    memberDao.update(teacher);
    teacherDao.update(teacher);
    
    return "redirect:list.do";
    
  }
  
}
