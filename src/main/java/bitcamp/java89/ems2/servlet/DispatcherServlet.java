package bitcamp.java89.ems2.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import bitcamp.java89.ems2.context.RequestHandlerMapping;
import bitcamp.java89.ems2.context.RequestHandlerMapping.RequestHandler;

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  ApplicationContext applicationContext;
  RequestHandlerMapping handlerMapping;
  
  @Override
  public void init() throws ServletException {
    applicationContext = 
        WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
    
    // 스프링 IoC 컨테이너에 들어있는 객체들의 이름을 가져온다.
    String[] names = applicationContext.getBeanDefinitionNames();
    
    // 객체를 저장할 바구니를 준비한다.
    ArrayList<Object> objList = new ArrayList<>();
    for (String name : names) {
      System.out.println(name);
      objList.add(applicationContext.getBean(name)); // 이름으로 객체를 찾아 objList에 담는다.
    }
    
    // 객체를 조사하여 @RequestMapping이 붙은 메서드를 따로 관리한다.
    handlerMapping = new RequestHandlerMapping(objList); //저장만 함.
    
  }
  
  
  
  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    try {
  //  클라이언트가 요청한 서블릿 주소 알아내기 
      String servletPath = request.getServletPath();
      
  //    RequestHandlerMapping 객체에서 클라이언트 요청을 처리할 메서드 정보를 찾는다.
      RequestHandler requestHandler = null;
      try {
        requestHandler = handlerMapping.getRequestHandler(servletPath);
      } catch (Exception e) {} //서블릿이 없으면 아래 실행-> jsp를 찾기 
      
  //    페이지 컨트롤러를 호출하여 작업을 실행시킨다. ###녹시작
      String viewUrl = null;
      if (requestHandler != null) {
        viewUrl = (String)requestHandler.method.invoke(requestHandler.obj,request,response);
      } else {
         viewUrl = servletPath.replaceAll(".do", ".jsp");
      }
      
      if (viewUrl.startsWith("redirect:")) {
        response.sendRedirect(viewUrl.substring(9));
      } else {
        //    페이지 컨트롤러가 리턴한 뷰 컨포넌트(JSP)로 화면 출력을 위임한다.
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
        rd.include(request, response);
      }
      
    } catch (Exception e) {
      request.setAttribute("error", e);
      request.setAttribute("title", "오류 발생!");
      request.setAttribute("contentPage", "/error.jsp");
      RequestDispatcher rd = request.getRequestDispatcher("/main.jsp");
      rd.forward(request, response);
      return;
    }
  } 
}






