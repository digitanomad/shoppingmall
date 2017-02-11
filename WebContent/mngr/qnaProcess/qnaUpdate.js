$(document).ready(function(){
	// [수정]버튼 클릭
    $("#update").click(function(){
    	  var qna_id = $("#qna_id").val();
  		  var query = {qna_content:$("#uRContent").val(),
  				       qna_id:$("#qna_id").val()};
  
  		  $.ajax({
  		     type: "POST",
  		     url: "/shoppingmall/mg/qnaReplyUpdatePro.do",
  		     data: query,
  		     success: function(data){
  		    	window.location.href("/shoppingmall/mg/qnaList.do");
  		     }
  		  });
	});
    
  // [취소]버튼 클릭
    $("#cancel").click(function(){
    	window.location.href("/shoppingmall/mg/qnaList.do");
	});  
});