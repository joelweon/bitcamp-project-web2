# bitcamp-project-web2

## 0.5 - 포워딩 적용
- 서블릿에서 오류가 발생하면 오류를 처리하는 서블릿으로 포워딩 시킨다.
- ErrorServlet 클래스 정의
- 서블릿 코드 변경

## 0.4 - 강사 관리 기능 구현
- domain 객체 구현
  - Teacher 클래스 구현
- DAO 객체 구현
  - TeacherMysqlDao 클래스 구현
- 서블릿 객체 구현
  - TeacherListServlet, TeacherAddServlet, TeacherDetailServlet, 
    TeacherUpdateServlet, TeacherDeleteServlet 클래스 구현 
        
        
## 0.3 - 매니저 관리 기능 구현
- domain 객체 구현
  - Manager 클래스 구현
- DAO 객체 구현
  - ManagerMysqlDao 클래스 구현
- 서블릿 객체 구현
  - ManagerListServlet, ManagerAddServlet, ManagerDetailServlet, 
    ManagerUpdateServlet, ManagerDeleteServlet 클래스 구현 
        
## 0.2 - 학생 관리 기능 구현
- 프로젝트 기본 패키지 준비
  - bitcamp.java89.ems2 패키지 생성
  
- domain 객체 구현
  - bitcamp.java89.ems2.domain 패키지 생성
  - Student.java 클래스 정의
- DAO 객체 구현
  - MemberDao, StudentDao, ManagerDao, TeacherDao 인터페이스 생성
  - MemberMysqlDao, StudentMysqlDao, ManagerMysqlDao, TeacherMysqlDao 클래스 정의
- 서블릿 구현
  - StudentListServlet, StudentAddServlet, StudentDetailServlet, 
    StudentUpdateServlet, StudentDeleteServlet 클래스 정의

## 0.1 - Gradle 웹 프로젝트 기본 골격 생성
- 소스 폴더 생성(/src/...)
- MySQL JDBC Driver 파일 준비(/libs/xxx.jar)
- Gradle 설정 파일 준비(/build.gradle)
- GitIgnore 설정 파일 준비(/.gitignore)
- README.md 파일 변경
