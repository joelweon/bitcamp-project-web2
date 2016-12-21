package bitcamp.java89.ems2.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/header")
public class HeaderServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    
    out.println("<div id='header' style='background-color:gray; height:57px;'>");
    out.println("<img src='../image/bit_logo.PNG'"
        + " height='50' style='float:left; margin-top:3px; margin-left:3px;'>");
    out.println("<div style='color:white; font-weight:bold;"
        + " margin-top:5px;");
    out.println("margin-left:55px; padding-top:15px; font-family:돋움체;"
        + " font-size: x-large;'>교육센터관리시스템</div></div>");
    
    out.println("</body>");
    out.println("</html>");
      
  }
}
