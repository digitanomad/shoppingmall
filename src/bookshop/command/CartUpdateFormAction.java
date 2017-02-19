package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CartUpdateFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		String cartId = request.getParameter("cart_id");
		String buyCount = request.getParameter("buy_count");
		
		request.setAttribute("cart_id", cartId);
		request.setAttribute("buy_count", buyCount);
		request.setAttribute("type", new Integer(1));
		
		return "/cart/cartUpdateForm.jsp";
	}

}
