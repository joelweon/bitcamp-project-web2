document.querySelector('#login-btn').onclick = function() {
	var param = {//json에서 속성명은 "email" 이렇게 해줘야하지만 javascript
		email: document.querySelector('#email').value,
		password: document.querySelector('#password').value,
		saveEmail: document.querySelector('#save-email').checked
	};
	
	var userTypeList = document.querySelectorAll('input[name=user-type]');//원하는 태그를 선택할때 사용하는 도구 -> selector(css문법)
	for (var i = 0; i < userTypeList.length; i++) {
		if (userTypeList[i].checked) {
			param.userType = userTypeList[i].value;
			break;
		}
	}
	
	post('login.json', param, function(jsonText) {
		var ajaxResult = JSON.parse(jsonText);
		if (ajaxResult.status == "success") {
			location.href = "../student/main.html";
			return;
		}
		
		alert(ajaxResult.data);//fail 이유 띄우기.
	})
//암호가 틀리면 서버에서 화면을 다시 받아옴.(네트워크 오버헤드)
//ajax는 서버에서 다시 데이터를 받아오는게 아닌 화면은 그 상태로 두고 오류 창만 띄움.(네트워크 오버헤드를 줄여준다.)
}

//email 쿠키가 있다면 값을 넣는다.
document.querySelector('#email').value = getCookie('email').replace(/"/g, '');
//  g: global 
//  i : 대/소문자구분x 
//  /: RegularExpression전체뒤져

// 이메일 저장을 체크하면 즉시 쿠키에 저장하고, 체크를 해제하면 즉시 쿠키에서 제거한다.
document.querySelector('#save-email').onclick = function(event) {
	console.log(this.email)
	//if (this.) //this 이벤트가 발생한 객체 즉 버튼
}









//하이브리드 개발과정.
//아래 부분이 AJAX로 UI 만들기다.
//이 함수는 서버에서 응답을 모두 받았을 때 호출된다. 그냥 등록만한 상태. 실행x -> 실행하면 al로 감  이게 비동기.
//학생 목록 가져와서 tr 태그를 만들어 붙인다.
/*get('list.json', function(jsonText) {// 프런트 컨트롤러가 do 말고 json이 있는 경우
  //result JSON  문자열을 자바스크립트 객체로 만든다.
  var ajaxResult = JSON.parse(jsonText);//파싱해버리기 json문자열을 자바스크립트로 바꿈  .ajaxResult이제 안해도됨.
  var status = ajaxResult.status;
  
  if (status != "success")
    return;
  
  var list = ajaxResult.data;
  var tbody = document.querySelector('#list-table > tbody');
  
  for (var student of list) {
    var tr = document.createElement("tr");
    tr.innerHTML = "<tr><td>" + 
      student.memberNo + "</td><td><a class='name-link' href='#' data-no='" + // 라벨붙이기-> class //data-no 권고사항
      student.memberNo +"'>" + 
      student.name + "</a></td><td>" + 
      student.tel + "</td><td>" + 
      student.working + "</td><td>" +
      student.grade + "</td><td>" +
      student.schoolName + "</td></tr>";
    tbody.appendChild(tr);  
  }
// 학생 목록에서 이름 링크에 click 이벤트를 처리한다.
  var al = document.querySelectorAll('.name-link');//All을 붙여야함.
  console.log(al);//가장 많이 쩌는 문제 비동기프로그램
//반복문이 다돌아야 존재한다! 그래서 여기다가 둔다.
  for (var a of al) {
	  a.onclick = function(event) {//onclick은 a에 속한다.
		event.preventDefault(); // a태그실행안되게 하기
		location.href = 'view.html?memberNo=' + this.getAttribute("data-no");//원래속성이아닌 임의로 사용자가 추가한 속성은 this.getAttribute를 해야한다!!! [data-no]이런식으로 처리할 수 없다!!
//만약a링크로하면 기존데이터를 가져온다 즉 자바스크립트 동작x  상세는 얘한테 넘기고 파라미터 값을 ㄴ 얘가 해결
// 모바일 생각.
	  }
  }
});
*/
// **main과 view html 은 변수를 공유할 수 없다. 페이지가 바뀌면 초기화된 상태로 다시 생긴다.
