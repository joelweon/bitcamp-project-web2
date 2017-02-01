function get(url, success) { //cb: 이쪽에서 호출할 함수를 만듦 success는 성공했을 때 넣는 변수
  var xhr = new XMLHttpRequest();//XMLHttpRequest이거로 객체를 얻음 어떤? send(http를 요청하는애)와 method를 갖고있다.
  xhr.onreadystatechange = function() {//상태를 보고할때 받는 onready
    if (xhr.readyState < 4) 
      return;
    success(xhr.responseText);
  }
  xhr.open('get', url, true);//true : 비동기
  xhr.send();
}
// 이렇게 라이브러리를 만들어 공유한다.
//이게 AJAX (비동기)


function post(url, data, success) {
	  var xhr = new XMLHttpRequest();
	  xhr.onreadystatechange = function() {
	    if (xhr.readyState < 4) 
	      return;
	    success(xhr.responseText);
	  }
	  xhr.open('post', url, true);
	  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
	  xhr.send(toQueryString(data));
	}


function toQueryString(obj) {
	var qs = "";
	for (var propName in obj) {
		if (qs.length > 0) {
			qs += "&";
		}
		qs += propName + "=" + obj[propName];
	}
	return qs;
}

// http://www.w3schools.com/js/js_cookies.asp
function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function setCookie(cname, cvalue, exdays, path) {//이름과 값과 expire + path
	if (path == undefined) {
		path = "/"; //현재경로에서 다 쓸 수 있다.
	}
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=" + path;
}





