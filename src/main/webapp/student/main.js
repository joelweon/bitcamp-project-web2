$.getJSON('list.json', function(ajaxResult) {
	  var status = ajaxResult.status;
  
  if (status != "success")
    return;
  
  var list = ajaxResult.data;
  var tbody = $('#list-table > tbody');
  
  for (var student of list) {
	  $("<tr>")
	  	.html("<td>" + 
		    student.memberNo + "</td><td><a class='name-link' href='#' data-no='" + 
		    student.memberNo + "'>" + 
		    student.name + "</a></td><td>" + 
		    student.tel + "</td><td>" + 
		    student.working + "</td><td>" +
		    student.grade + "</td><td>" +
		    student.schoolName + "</td>")
		.appendTo(tbody);
  }
// 학생 목록에서 이름 링크에 click 이벤트를 처리한다.
	$('.name-link').click(function(event) {
		event.preventDefault();
		location.href = 'view.html?memberNo=' + this.getAttribute("data-no");
	});
});

$('#new-btn').click(function(event) { 
  event.preventDefault();
  location.href = 'view.html';
});
