package bitcamp.java89.ems2.control;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import bitcamp.java89.ems2.dao.ManagerDao;
import bitcamp.java89.ems2.dao.MemberDao;
import bitcamp.java89.ems2.dao.StudentDao;
import bitcamp.java89.ems2.dao.TeacherDao;
import bitcamp.java89.ems2.domain.Manager;
import bitcamp.java89.ems2.domain.Member;
import bitcamp.java89.ems2.util.MultipartUtil;

@Controller
public class ManagerControl {
  @Autowired ServletContext sc;
  
  @Autowired StudentDao studentDao;
  @Autowired MemberDao memberDao;
  @Autowired TeacherDao teacherDao;
  @Autowired ManagerDao managerDao;

  @RequestMapping("/manager/list")
  public String list(Model model) throws Exception {
    
    ArrayList<Manager> list = managerDao.getList();
    model.addAttribute("managers", list);
    model.addAttribute("title", "매니저관리-목록");
    model.addAttribute("contentPage", "/manager/list.jsp");
    return "main";
  }
  
  @RequestMapping("/manager/detail")
  public String detail(int memberNo, Model model) throws Exception {
    Manager manager = managerDao.getOne(memberNo);
    
    if (manager == null) {
      throw new Exception("해당 아이디의 매니저 없습니다.");
    }
    
    model.addAttribute("manager", manager);
    model.addAttribute("title", "매니저관리-상세정보");
    model.addAttribute("contentPage", "/manager/detail.jsp");

    return "main";
  }
  
  @RequestMapping("/manager/add")
  public String add(Manager manager, MultipartFile photo) throws Exception {
    
    if (managerDao.count(manager.getEmail()) > 0) {
      throw new Exception("같은 매니저 이메일이 존재합니다. 등록을 취소합니다.");
    }
    
    if (memberDao.count(manager.getEmail()) == 0) { // 강사나 매니저로 등록되지 않았다면,
      memberDao.insert(manager);
      
    } else { // 강사나 매니저로 이미 등록된 사용자라면 기존의 회원 번호를 사용한다.
      Member member = memberDao.getOne(manager.getEmail());
      manager.setMemberNo(member.getMemberNo());
    }
    
    if (photo.getSize() > 0) { //파일이 업로드 되었다면
      String newFilename = MultipartUtil.generateFilename();
      photo.transferTo(new File(sc.getRealPath("/upload/" + newFilename))); //임시
      manager.setPhotoPath(newFilename); //디비에 이름 저장
    }
    
    managerDao.insert(manager);
    return "redirect:list.do";
  }
  
  @RequestMapping("/manager/delete")
  public String delete(int memberNo, HttpServletResponse response) throws Exception {
    
    if (managerDao.countByNo(memberNo) == 0) {
      throw new Exception("사용자를 찾지 못했습니다.");
    }
    
    managerDao.delete(memberNo);
    
    
    if (studentDao.countByNo(memberNo) == 0 && teacherDao.countByNo(memberNo) == 0) {
      memberDao.delete(memberNo);
    }
    
    return "redirect:list.do";
    
  }
  
  @RequestMapping("/manager/update")
  public String update(Manager manager, MultipartFile photo) throws Exception {
    
    if (managerDao.countByNo(manager.getMemberNo()) == 0) {
      throw new Exception("사용자를 찾지 못했습니다.");
    }
    
    memberDao.update(manager);
    
    if (photo.getSize() > 0) { //파일이 업로드 되었다면
      String newFilename = MultipartUtil.generateFilename();
      photo.transferTo(new File(sc.getRealPath("/upload/" + newFilename))); //임시
      manager.setPhotoPath(newFilename); //디비에 이름 저장
    }
    
    managerDao.update(manager);
    
    return "redirect:list.do";
    
  }
}
