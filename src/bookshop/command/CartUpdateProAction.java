package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.CartDBBean;

public class CartUpdateProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		int cartId = Integer.parseInt(request.getParameter("cart_id"));
		byte buyCount = Byte.parseByte(request.getParameter("buy_count"));
		
		// cart_id에 해당하는 buy_count의 값을 수정
		CartDBBean bookProcess = CartDBBean.getInstance();
		bookProcess.updateCount(cartId, buyCount);
		
		request.setAttribute("type", new Integer(1));
		return "/cart/cartUpdatePro.jsp";
	}

}
