window.addEventListener("load", function() {//로딩페이지: 페이지 다 실행 후 실행. => html 로딩을 보장. 
	//window.onload의 단점 다른 개발자가 덮어쓰면 못쓰게된다. 그래서 새로 추가 addEventListener
	
	//header.html을 가져와서 붙인다.
	get('../header.html', function(result) {
	  document.querySelector('#header').innerHTML = result;
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