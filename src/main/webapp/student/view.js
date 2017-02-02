
try {
	var memberNo = location.href.split('?')[1].split('=')[1];
} catch (error) {
	var memberNo = -1;
}

if (memberNo > 0) {
	prepareViewForm();
} else {
	prepareNewForm();
}

function prepareViewForm() {
	//등록버튼은 감춘다.
	$('.new-form').css('display', 'none');
    /*for (var i = 0; i < tags.length; i++) {
		tags[i].style.display = 'none';}   */
	
  //학생 목록 가져와서 tr 태그를 만들어 붙인다.
  getJSON('detail.json?memberNo=' + memberNo, function(ajaxResult) {// 프런트 컨트롤러가 do 말고 json이 있는 경우
	    /* @RestController 페이지 컨트롤러의 메서드가 리턴한 값은 
	     * AjaxResult의 프로퍼티 값으로 JSON 문자열을 만든다.
	     * 따라서 status나 data 프로퍼티를 바로 꺼내면 된다.
	     */
    //result JSON  문자열을 자바스크립트 객체로 만든다.
    var status = ajaxResult.status;
    
    if (status != "success") {
  	  alert(ajaxResult.data);
      return;
    }
    
    var student = ajaxResult.data;
    console.log(student);
    
    document.querySelector('#email').value = student.email;
    document.querySelector('#name').value = student.name;
    document.querySelector('#tel').value = student.tel;
    if (student.working) {
      document.querySelector('#working').checked = 'checked';
    } else  {
      document.querySelector('#not-working').checked = 'checked' ;
    }
      document.querySelector('#grade').value = student.grade;
      document.querySelector('#school-name').value = student.schoolName;
      document.querySelector('#photo-img').src = "../upload/" + student.photoPath;
  });
  
  // 삭제, 변경 버튼을 클릭했을 때 호출될 함수(클릭 이벤트 핸들러) 등록
  document.querySelector('#delete-btn').onclick = function() {
	  get('delete.json?memberNo=' + memberNo, function(jsonText) {
		  var ajaxResult = JSON.parse(jsonText);
		  if (ajaxResult.status != "success") {
			  alert(ajaxResult.data);
			  return;
		  }
		  location.href = 'main.html';
	  })
  }
  
  document.querySelector('#update-btn').onclick = function() {
	  var param = {
			memberNo: memberNo, //프로퍼티이름:변수명(값)
			"name": document.querySelector('#name').value,
			tel: document.querySelector('#tel').value,
			email: document.querySelector('#email').value,
			password: document.querySelector('#password').value,
			working: document.querySelector('#working').checked,
			grade: document.querySelector('#grade').value,
			schoolName: document.querySelector('#school-name').value,
	  };
	  
	  post('update.json', param, function(jsonText) {
		  var ajaxResult = JSON.parse(jsonText)
		  if (ajaxResult.status != "success") {
			  alert(ajaxResult.data);
			  return;
		  }
		  location.href = 'main.html';
	  });
  }
}// prepareViewForm()
  

function prepareNewForm() {
	 //변경 삭제 버튼을 감춘다.
  var tags = document.querySelectorAll('.view-form');
  for (var i = 0; i < tags.length; i++) {
    tags[i].style.display = 'none';
  }
}// prepareNewForm()

  document.querySelector('#add-btn').onclick = function() {
	  var param = {
			"name": document.querySelector('#name').value,
			tel: document.querySelector('#tel').value,
			email: document.querySelector('#email').value,
			password: document.querySelector('#password').value,
			working: document.querySelector('#working').checked,
			grade: document.querySelector('#grade').value,
			schoolName: document.querySelector('#school-name').value,
	  };
	  
	  post('add.json', param, function(jsonText) {
		  var ajaxResult = JSON.parse(jsonText)
		  if (ajaxResult.status != "success") {
			  alert(ajaxResult.data);
			  return;
		  }
		  location.href = 'main.html';
	  });
  }
  
// 목록버튼을 클릭했을 때 호출될 함수(이벤트핸들러) 등록!
document.querySelector('#list-btn').onclick = function() {
	location.href = 'main.html';
};
