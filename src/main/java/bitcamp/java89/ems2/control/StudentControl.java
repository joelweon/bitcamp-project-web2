package bitcamp.java89.ems2.control;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import bitcamp.java89.ems2.domain.Student;
import bitcamp.java89.ems2.service.StudentService;
import bitcamp.java89.ems2.util.MultipartUtil;

@Controller
public class StudentControl {
  @Autowired ServletContext sc;
  
  @Autowired StudentService studentService;

  @RequestMapping("/student/form")
  public String form(Model model) {
    model.addAttribute("title", "학생 입력폼");
    model.addAttribute("contentPage", "student/form.jsp");
    return "main";
  }
  
  
  @RequestMapping("/student/list")
  public String list(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="5") int pageSize,
      Model model) throws Exception {
    
    if (pageNo < 1) {
      pageNo = 1;
    }
    
    if (pageSize < 5 || pageSize > 20) {
      pageSize = 5;
    }
    
    List<Student> list = studentService.getList(pageNo, pageSize);
    
    model.addAttribute("students", list); //이건 페이지 컨트롤러의 일. 바뀐것은 서비스를 경로.Dao직접쓰지x
    model.addAttribute("title", "학생관리-목록");
    model.addAttribute("contentPage", "student/list.jsp");
    return "main";
  }
  
  @RequestMapping("/student/detail")
  public String detail(int memberNo, Model model) throws Exception {
    Student student = studentService.getDetail(memberNo);
    
    if (student == null) {
      throw new Exception("해당 학생이 없습니다.");
    }
    
//    페이지 컨트롤러는 모델객체가 리턴한 값을 JSP가 사용할 수 있도록 가공하는 일을 한다.
    model.addAttribute("student", student);
    model.addAttribute("title", "학생관리-상세정보");
    model.addAttribute("contentPage", "student/detail.jsp");

    return "main";
  }
  
  @RequestMapping("/student/add")
  public String add(Student student, MultipartFile photo) throws Exception {
    
    if (photo.getSize() > 0) { //파일이 업로드 되었다면
      String newFilename = MultipartUtil.generateFilename();
      photo.transferTo(new File(sc.getRealPath("/upload/" + newFilename))); //임시
      student.setPhotoPath(newFilename); //디비에 이름 저장
    }//페이지 컨트롤러 입력된 값을 가공하는 역할.(pagecont에서 관리 
    
    studentService.add(student);
    return "redirect:list.do";
  }

  @RequestMapping("/student/delete")
  public String delete(int memberNo, HttpServletRequest request) throws Exception {
    
    studentService.delete(memberNo);
    
    return "redirect:list.do";
  }
  
  @RequestMapping("/student/update")
  public String update(Student student, MultipartFile photo) throws Exception {
    
    //페이지 컨트롤러가 할일.
    if (photo.getSize() > 0) { //파일이 업로드 되었다면
      String newFilename = MultipartUtil.generateFilename();
      photo.transferTo(new File(sc.getRealPath("/upload/" + newFilename))); //임시
      student.setPhotoPath(newFilename); //디비에 이름 저장
    }
    
    studentService.update(student);
    
    return "redirect:list.do";
  }
}
