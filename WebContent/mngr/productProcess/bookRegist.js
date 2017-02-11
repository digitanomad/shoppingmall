$(document).ready(function(){//[책등록]버튼 클릭
	$("#upForm1").ajaxForm({//이미지를 포함한 상품등록
		success: function(data, status){//업로드에 성공하면 수행
   			window.location.href("/shoppingmall/mg/bookList.do?book_kind=all");
   		}
    });
	
	$("#bookMain").click(function(){//[관리자 메인으로]버튼 클릭
		window.location.href("/shoppingmall/mg/managerMain.do");
	});
	
	$("#bookList").click(function(){//[목록으로]버튼 클릭
		window.location.href("/shoppingmall/mg/bookList.do?book_kind=all");
	});
});