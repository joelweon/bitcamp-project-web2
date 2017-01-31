window.addEventListener("load", function() {//로딩페이지: 페이지 다 실행 후 실행. => html 로딩을 보장. 
	//window.onload의 단점 다른 개발자가 덮어쓰면 못쓰게된다. 그래서 새로 추가 addEventListener
	
	//header.html을 가져와서 붙인다.
	get('../header.html', function(result) {
//    서버에서 로그인 사용자 정보를 가져온다.
	  get('../auth/loginUser.json', function(jsonText) {
		var ajaxResult = JSON.parse(jsonText);
		
		document.querySelector('#header').innerHTML = result;
		
		if (ajaxResult.status == "fail") { // 로그인 되지 않았으면,
			// 로그온 상태 출력 창을 감춘다.
			document.querySelector('#logon-div').style.display='none';
			return;
		}
		
		// 로그인 되었으면, 로그오프 상태 출력 창을 감춘다.
		document.querySelector('#logoff-div').style.display='none';
		document.querySelector('#logon-div img').src = //src 원래있는 것. setAttribute안해도됨.
			'../upload/' + ajaxResult.data.photoPath;
		document.querySelector('#logon-div span').textContent = //innerHTML은 태그를 살린다. 사람이름에 해킹코드 동작안되게
			ajaxResult.data.name;
	  })
	});
	
	// sidebar.html을 가져와서 붙인다.
	get('../sidebar.html', function(result) {
	  document.querySelector('#sidebar').innerHTML = result;
	});
	
	// footer.html을 가져와서 붙인다.
	get('../footer.html', function(result) {
	  document.querySelector('#footer').innerHTML = result;
	});
});
//비동기로 작동함 하나씩 다 일시킴.


// 이렇게 라이브러리를 만들어 공유한다.
// 얘는 bit.js를 사용한다.

// 라이브러리를 쓸때 서로 연관관계를 생각해서 선언 순서를 잘 설정해 줘야한다.