var status = true;

$(document).ready(function(){
	//[상품등록]버튼 클릭
	$("#registProduct").click(function(){
		window.location.href("/shoppingmall/mg/bookRegisterForm.do");
	});
	
	//[상품수정/삭제]버튼 클릭
	$("#updateProduct").click(function(){
		window.location.href("/shoppingmall/mg/bookList.do?book_kind=all");
	});
	
	//[전체구매목록 확인]버튼 클릭
	$("#orderedProduct").click(function(){
		window.location.href("/shoppingmall/mg/orderList.do");
	});
	
	//[상품 QnA답변]버튼 클릭
	$("#qna").click(function(){
		window.location.href("/shoppingmall/mg/qnaList.do");
	});
});