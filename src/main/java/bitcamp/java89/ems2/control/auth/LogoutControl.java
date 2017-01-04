package bitcamp.java89.ems2.control.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import bitcamp.java89.ems2.control.PageController;

@Controller("/auth/logout.do")
public class LogoutControl  implements PageController {
  @Override
  public String service(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.getSession().invalidate(); // 기존 세션을 무효화시킨다.
    // => 세션을 무효화시키는 순간 세션에 저장된 모든 데이터를 제거된다.
    
    return "redirect:loginform.do";
  }
}








