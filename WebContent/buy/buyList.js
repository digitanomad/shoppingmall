$(document).ready(function() {
	// [쇼핑계속]버튼 클릭
	$("#conShopping").click(function() {
		window.location.href("/shoppingmall/list.do?book_kind=all");
	});
	
	// [메인으로]버튼 클릭
	$("#shopMain").click(function() {
		window.location.href("/shoppingmall/index.do");
	});
});
