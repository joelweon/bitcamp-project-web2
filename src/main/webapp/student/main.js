// 처음에는 1페이지 5개를 로딩한다.
var currPageNo = 1;
var pageSize = 5;
loadList(currPageNo, 5);


$('#new-btn').click(function(event) {
  event.preventDefault(); 
  location.href = 'view.html';
});


$('#prevPgBtn').click(function() {
    loadList(--currPageNo, 5);
});

$('#nextPgBtn').click(function() {
  loadList(++currPageNo, 5);
});

function preparePagingButton(totalCount) {
//  현재 페이지 번호가 1이면 이전 버튼을 비활성화 시킨다.
  if (currPageNo <= 1) {
    $('#prevPgBtn').attr('disabled', true);
  } else {
    $('#prevPgBtn').attr('disabled', false);
  }
  
  var maxPageNo = parseInt(totalCount / pageSize);
  if ((totalCount % pageSize) > 0) {
    maxPageNo++;
  }
  
  if (currPageNo >= maxPageNo) {
    $('#nextPgBtn').attr('disabled', true);
  } else {
    $('#nextPgBtn').attr('disabled', false);
  }
  
  // 현재 페이지 번호를 출력한다.
  $('#pageNo').text(currPageNo);
}


function loadList(pageNo,pageSize) {
//학생 목록 가져와서 tr 태그를 만들어 붙인다.
  $.getJSON('/bitcamp-project-web2/student/list.json',
  {
    "pageNo": pageNo,
    "pageSize": pageSize
  },
  function(ajaxResult) {
    var status = ajaxResult.status;
    
    if (status != "success")
      return;
    
    var list = ajaxResult.data.list;
    var tbody = $('#list-table > tbody');
    
    // 템플릿 텍스트를 처리하여 HTML을 생성해 줄 함수 얻기
    var template = Handlebars.compile($('#trTemplate').html());
    
    
    // 템플릿 엔진을 통해 생성된 HTML을 tbody에 넣는다.
    tbody.html(template({"list": list}));
    
    // 학생 목록에서 이름 링크에 click 이벤트를 처리한다.
    $('.name-link').click(function(event) {
      event.preventDefault();
      location.href = 'view.html?memberNo=' + $(this).attr("data-no");
    });
    
      // 페이지 버튼 설정
    preparePagingButton(ajaxResult.data.totalCount);
  });
}

