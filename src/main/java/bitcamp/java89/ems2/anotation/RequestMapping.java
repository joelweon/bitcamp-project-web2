package bitcamp.java89.ems2.anotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 실행할 때 이 애노테이션의 값을 꺼낼 수 있게 하자!
@Retention(RetentionPolicy.RUNTIME)
                       // 메서드 찾아 호출
public @interface RequestMapping {
  String value() default ""; //여기에 URL
}
