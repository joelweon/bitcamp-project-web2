$('#submit-btn').click(function() {
  // 이메일 저장이 체크되어 있으면 쿠키에 저장하고, 
  // 체크되어 있지 않으면 쿠키에서 제거한다.
  if ($('#save-email').is(':checked')) {
    setCookie('email', $('#email').val(), 30);
  } else {
    setCookie('email', '', 0);
    //setCookie('email', '', 0, 'bitcamp-project-web2/auth');
  }
  
  var param = {
    email: $('#email').val(),
    password: $('#password').val(),
    userType: $('input[name=user-type]:checked').val()
  };
  
  $.post('login.json', param, function(ajaxResult) {
    if (ajaxResult.status == "success") {
      location.href = "../student/main.html"; 
      return;
    }
    alert(ajaxResult.data);
  }, 'json');
  
}); // click()

// email 쿠키가 있다면 값을 넣는다.
$('#email').val(getCookie('email').replace(/"/g, ''));





