package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.CartDBBean;
import bookshop.bean.CartDataBean;

public class InsertCartAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		// 장바구니에 추가할 정보를 파라미터에서 받아냄
		byte buyCount = Byte.parseByte(request.getParameter("buy_count"));
		int bookId = Integer.parseInt(request.getParameter("book_id"));
		String bookTitle = request.getParameter("book_title");
		String bookImage = request.getParameter("book_image");
		int buyPrice = (int)Float.parseFloat(request.getParameter("buy_price"));
		String buyer = request.getParameter("buyer");
		
		// 장바구니에 추가하기 위한 정보구성
		CartDataBean cart = new CartDataBean();
		cart.setBookId(bookId);
		cart.setBookImage(bookImage);
		cart.setBookTitle(bookTitle);
		cart.setBuyCount(buyCount);
		cart.setBuyPrice(buyPrice);
		cart.setBuyer(buyer);
		
		// 장바구니에 추가
		CartDBBean bookProcess = CartDBBean.getInstance();
		bookProcess.insertCart(cart);
		
		return "/cart/insertCart.jsp";
	}

}
