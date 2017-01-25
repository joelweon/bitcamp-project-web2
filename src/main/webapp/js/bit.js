function get(url, success) { //cb: 이쪽에서 호출할 함수를 만듦 success는 성공했을 때 넣는 변수
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function() {
    if (xhr.readyState < 4) 
      return;
    success(xhr.responseText);
  }
  xhr.open('get', url, true);
  xhr.send();
}
// 이렇게 라이브러리를 만들어 공유한다.