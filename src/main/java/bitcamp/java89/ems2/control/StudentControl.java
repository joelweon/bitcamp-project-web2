package bitcamp.java89.ems2.control;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import bitcamp.java89.ems2.dao.ManagerDao;
import bitcamp.java89.ems2.dao.MemberDao;
import bitcamp.java89.ems2.dao.StudentDao;
import bitcamp.java89.ems2.dao.TeacherDao;
import bitcamp.java89.ems2.domain.Member;
import bitcamp.java89.ems2.domain.Student;
import bitcamp.java89.ems2.util.MultipartUtil;

@Controller
public class StudentControl {
  @Autowired ServletContext sc;
  
  @Autowired StudentDao studentDao;
  @Autowired MemberDao memberDao;
  @Autowired TeacherDao teacherDao;
  @Autowired ManagerDao managerDao;
  
  @RequestMapping("/student/list")
  public String list(Model model) throws Exception {
    ArrayList<Student> list = studentDao.getList();
    model.addAttribute("students", list);
    model.addAttribute("title", "학생관리-목록");
    model.addAttribute("contentPage", "/student/list.jsp");
    return "main";
  }
  
  @RequestMapping("/student/detail")
  public String detail(int memberNo, Model model) throws Exception {
    Student student = studentDao.getOne(memberNo);
    
    if (student == null) {
      throw new Exception("해당 학생이 없습니다.");
    }
    
    model.addAttribute("student", student);
    model.addAttribute("title", "학생관리-상세정보");
    model.addAttribute("contentPage", "/student/detail.jsp");

    return "main";
  }
  
  @RequestMapping("/student/add")
  public String add(Student student, MultipartFile photo) throws Exception {
    
    if (studentDao.count(student.getEmail()) > 0) {
      throw new Exception("같은 학생의 이메일이 존재합니다. 등록을 취소합니다.");
    }
    
    if (memberDao.count(student.getEmail()) == 0) { // 강사나 매니저로 등록되지 않았다면,
      memberDao.insert(student);
      
    } else { // 강사나 매니저로 이미 등록된 사용자라면 기존의 회원 번호를 사용한다.
      Member member = memberDao.getOne(student.getEmail());
      student.setMemberNo(member.getMemberNo());
    }
    
    if (photo.getSize() > 0) { //파일이 업로드 되었다면
      String newFilename = MultipartUtil.generateFilename();
      photo.transferTo(new File(sc.getRealPath("/upload/" + newFilename))); //임시
      student.setPhotoPath(newFilename); //디비에 이름 저장
    }
    
    studentDao.insert(student);
    return "redirect:list.do";
  }

  @RequestMapping("/student/delete")
  public String delete(int memberNo, HttpServletRequest request) throws Exception {
    
    if (studentDao.countByNo(memberNo) == 0) {
      throw new Exception("학생을 찾지 못했습니다.");
    }
    
    studentDao.delete(memberNo);
    
    if (managerDao.countByNo(memberNo) == 0 && teacherDao.countByNo(memberNo) == 0) {
      memberDao.delete(memberNo);
    }
    
    return "redirect:list.do";
  }
  
  @RequestMapping("/student/update")
  public String update(Student student, MultipartFile photo) throws Exception {
    
    if (studentDao.countByNo(student.getMemberNo()) == 0) {
      throw new Exception("학생을 찾지 못했습니다.");
    }
    
    
    memberDao.update(student);
    
    if (photo.getSize() > 0) { //파일이 업로드 되었다면
      String newFilename = MultipartUtil.generateFilename();
      photo.transferTo(new File(sc.getRealPath("/upload/" + newFilename))); //임시
      student.setPhotoPath(newFilename); //디비에 이름 저장
    }
    
    studentDao.update(student);
    
    return "redirect:list.do";
  }
}
