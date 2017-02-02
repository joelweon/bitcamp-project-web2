var bit = function(selector) {
	
	if (selector.indexOf('<') > -1) {
		var tags = [document.createElement(selector.substr(1, selector.length - 2))];
	} else {
		var tags = document.querySelectorAll(selector);
	}
	
	// querySelectorAll()이 리턴한 객체에 함수를 추가한다.
	// 1) 목록에서 각 태그에 자식 태그를 붙인다.
	tags.append = function(childs) {
		for (var i = 0; i < this.length; i++) {
			for (var j = 0; j < childs.length; j++) {
				this[i].appendChild(childs[j]);
			}
		}
		return this;
	};
	
	// 2) 목록에서 각 태그의 innerHTML 값을 설정한다.
	tags.html = function(content) {
		for (var i = 0; i < this.length; i++) {
			this[i].innerHTML = content;
		}
		return this;
	};
	
	// 3) 자식 태그 목록에서 한 개의 자식 태그를 꺼내고, 
	//    그 자식 태그를 부모에 붙인다.
	tags.appendTo = function(parents) {
		for (var i = 0; i < parents.length; i++) {
			for (var j = 0; j < this.length; j++) {
				parents[i].appendChild(this[j]);
			}
		}
		return this;
	};
	
	// 4) 목록에서 각각의 태그를 꺼내 클릭 이벤트 핸들러를 등록한다.
	tags.click = function(cb) {
		for (var i = 0; i < this.length; i++) {
			this[i].addEventListener('click', cb);
		}
		return this;
	};
	
	// 5) 목록에서 태그를 꺼내 스타일을 지정한다.
	tags.css = function(propName, value) {
		for (var i = 0; i < this.length; i++) {
			this[i].style[propName] = value; //propName이 문자열로 오기 때문에 대괄호 써줌.
		}
		return this;
	};
	
	
	
	return tags;
};


bit.get = function(url, success) {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
	  if (xhr.readyState < 4) 
	    return;
	  success(xhr.responseText);
	}
	xhr.open('get', url, true);
	xhr.send();
};

bit.getJSON = function(url, success) {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
	  if (xhr.readyState < 4) 
	    return;
	  success(JSON.parse(xhr.responseText));
	}
	xhr.open('get', url, true);
	xhr.send();
};

function get(url, success) {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
	  if (xhr.readyState < 4) 
	    return;
	  success(xhr.responseText);
	}
	xhr.open('get', url, true);
	xhr.send();
}

function post(url, data, success) {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
	  if (xhr.readyState < 4) 
	    return;
	  success(xhr.responseText);
	}
	xhr.open('post', url, true);
	xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
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

function setCookie(cname, cvalue, exdays, path) {
	if (path == undefined) {
		path = "/";
	}
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=" + path;
}

var $ = bit; 














