package bitcamp.java89.ems2.control.advice;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice// 페이지 컨트롤러가 작업하기 전에 충고해주는 애
public class WebControllerAdvice {//애노테이션으로 끝.

  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //매번 객체생성은 낭비-> 여기로 뻄.

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));//커스텀에디터 등록
  }
}
