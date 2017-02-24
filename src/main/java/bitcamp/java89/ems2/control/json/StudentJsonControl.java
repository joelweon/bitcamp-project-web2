package bitcamp.java89.ems2.control.json;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bitcamp.java89.ems2.domain.Student;
import bitcamp.java89.ems2.service.StudentService;

//@Controller
@RestController // 이 애노테이션을 붙이면, 스프링 설정 파일에 JSON 변환기 'MappingJackson2JsonView' -가장 최신기술.
public class StudentJsonControl {
  @Autowired ServletContext sc;
  
  @Autowired StudentService studentService;
  
  @RequestMapping("/student/list")
  public AjaxResult list(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="5") int pageSize) throws Exception {
    
    if (pageNo < 1) {
      pageNo = 1;
    }
    
    if (pageSize < 5 || pageSize > 20) {
      pageSize = 5;
    }
    
    List<Student> list = studentService.getList(pageNo,pageSize);
    int totalCount = studentService.getSize();
    
//    list와 totalCount 두개를 보내기 위해 HashMap 생성.
    HashMap<String,Object> resultMap = new HashMap<>();
    resultMap.put("list", list);
    resultMap.put("totalCount", totalCount);
    
    return new AjaxResult(AjaxResult.SUCCESS, resultMap);
  }
  
  @RequestMapping("/student/detail")
  public AjaxResult detail(int memberNo) throws Exception {
    Student student = studentService.getDetail(memberNo);
    
    if (student == null) {
      return new AjaxResult(AjaxResult.FAIL,"해당 학생이 없습니다.");
    }
    return new AjaxResult(AjaxResult.SUCCESS, student);
  }
  
  @RequestMapping("/student/add")
  public AjaxResult add(Student student) throws Exception {
    studentService.add(student);
    return new AjaxResult(AjaxResult.SUCCESS, "등록 성공입니다.");
  }

  @RequestMapping("/student/delete")
  public AjaxResult delete(int memberNo, HttpServletRequest request) throws Exception {
    
    int count = studentService.delete(memberNo);
    if (count == 0) {
      return new AjaxResult(AjaxResult.FAIL, "해당 번호의 학생이 없습니다.");
    }
    return new AjaxResult(AjaxResult.SUCCESS, "삭제 성공입니다.");
  }
  
  @RequestMapping("/student/update")
  public AjaxResult update(Student student) throws Exception {

    int count = studentService.update(student);

    if (count == 0) {
      return new AjaxResult(AjaxResult.FAIL, "해당 번호의 학생이 없습니다.");
    }
    
    return new AjaxResult(AjaxResult.SUCCESS, "변경 성공입니다.");
  }
}
